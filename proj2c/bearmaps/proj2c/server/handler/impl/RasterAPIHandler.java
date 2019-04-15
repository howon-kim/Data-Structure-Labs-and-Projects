package bearmaps.proj2c.server.handler.impl;

import bearmaps.proj2c.AugmentedStreetMapGraph;
import bearmaps.proj2c.server.handler.APIRouteHandler;
import edu.princeton.cs.algs4.In;
import spark.Request;
import spark.Response;
import bearmaps.proj2c.utils.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static bearmaps.proj2c.utils.Constants.SEMANTIC_STREET_GRAPH;
import static bearmaps.proj2c.utils.Constants.ROUTE_LIST;

/**
 * Handles requests from the web browser for map images. These images
 * will be rastered into one large image to be displayed to the user.
 * @author rahul, Josh Hug, _________
 */

public class RasterAPIHandler extends APIRouteHandler<Map<String, Double>, Map<String, Object>> {

    private static final double ULLON = -122.2998046875;
    private static final double LRLON = -122.2119140625;
    private static final double ULLAT = 37.892195547244356;
    private static final double LRLAT = 37.82280243352756;
    private static final double INTLON = (LRLON - ULLON);
    private static final double INTLAT = (ULLAT - LRLAT);


    /**
     * Each raster request to the server will have the following parameters
     * as keys in the params map accessible by,
     * i.e., params.get("ullat") inside RasterAPIHandler.processRequest(). <br>
     * ullat : upper left corner latitude, <br> ullon : upper left corner longitude, <br>
     * lrlat : lower right corner latitude,<br> lrlon : lower right corner longitude <br>
     * w : user viewport window width in pixels,<br> h : user viewport height in pixels.
     **/
    private static final String[] REQUIRED_RASTER_REQUEST_PARAMS = {"ullat", "ullon", "lrlat",
            "lrlon", "w", "h"};

    /**
     * The result of rastering must be a map containing all of the
     * fields listed in the comments for RasterAPIHandler.processRequest.
     **/
    private static final String[] REQUIRED_RASTER_RESULT_PARAMS = {"render_grid", "raster_ul_lon",
            "raster_ul_lat", "raster_lr_lon", "raster_lr_lat", "depth", "query_success"};


    @Override
    protected Map<String, Double> parseRequestParams(Request request) {
        return getRequestParams(request, REQUIRED_RASTER_REQUEST_PARAMS);
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param requestParams Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @param response : Not used by this function. You may ignore.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image;
     *                    can also be interpreted as the length of the numbers in the image
     *                    string. <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    @Override
    public Map<String, Object> processRequest(Map<String, Double> requestParams, Response response) {
        System.out.println("yo, wanna know the parameters given by the web browser? They are:");
        System.out.println(requestParams);
        Map<String, Object> results = new HashMap<>();
        System.out.println("Since you haven't implemented RasterAPIHandler.processRequest, nothing is displayed in "
                + "your browser.");

        int [] position =  findLocation(requestParams);
        if (position == null) {
            results.put("raster_ul_lon", ULLON);
            results.put("raster_lr_lon", ULLON);
            results.put("raster_ul_lat", ULLAT);
            results.put("raster_lr_lat", ULLAT);
            results.put("render_grid", new String[0][0]);
            results.put("depth", 0);
            results.put("query_success", false);
            return results;
        }
        int x_left = position[0];
        int x_right = position[1];
        int y_left = position[2];
        int y_right = position[3];
        int depth = position[4];
        String[][] render_grid = new String[y_right - y_left + 1][x_right - x_left + 1];
        int row = 0; int col = 0;

        for (int i = y_left; i <= y_right; i++){
            for (int j = x_left; j <= x_right; j++){
                render_grid[row][col] = "d" + depth + "_x" + j + "_y" + i + ".png";
                col++;
            }
            row++;
            col = 0;
        }

        double interval_lon = INTLON  / Math.pow(2, depth);
        double interval_lat = INTLAT / Math.pow(2, depth);

        results.put("raster_ul_lon", ULLON + interval_lon * x_left);
        results.put("raster_lr_lon", ULLON + interval_lon * (x_right + 1));
        results.put("raster_ul_lat", ULLAT - interval_lat * y_left);
        results.put("raster_lr_lat", ULLAT - interval_lat * (y_right + 1));
        results.put("render_grid", render_grid);
        results.put("depth", depth);
        results.put("query_success", true);
        return results;

    }

    private double londpp(Map<String, Double> requestParams) {
        double ullon = requestParams.get("ullon");
        double lrlon = requestParams.get("lrlon");
        double width = requestParams.get("w");
        return (lrlon - ullon) / width;
    }

    private double londpp(double ullon, double lrlon, double width) {
        return (lrlon - ullon) / width;
    }

    private int findDepth(Map<String, Double> requestParams) {
        int depth = 0;
        double curr_londpp = londpp(ULLON, LRLON, 256);
        double user_londpp = londpp(requestParams);
        System.out.println(curr_londpp + "user: " + user_londpp);

        while (curr_londpp >= user_londpp && depth < 7) {

            curr_londpp /= 2;
            depth += 1;
        }
        return depth;
    }

    private int[] findLocation(Map<String, Double> requestParams) {
        int depth = findDepth(requestParams);
        double user_ullon = requestParams.get("ullon");
        double user_lrlon = requestParams.get("lrlon");
        double user_ullat = requestParams.get("ullat");
        double user_lrlat = requestParams.get("lrlat");

        double interval_lon = (LRLON - ULLON)  / Math.pow(2, depth);
        double interval_lat = (ULLAT - LRLAT) / Math.pow(2, depth);

        int pos_ullon, pos_lrlon, pos_ullat, pos_lrlat;

        if(user_ullon < ULLON || user_ullon > LRLON
        && user_lrlon < ULLON || user_lrlon > LRLON
        && user_ullat > ULLAT || user_ullat < LRLAT
        && user_lrlat > ULLAT || user_ullat < LRLAT) {
            return null;
        }
        if(user_ullon < ULLON) {
            pos_ullon = 0;
        } else {
            pos_ullon = (int) ((user_ullon - ULLON) / interval_lon);
        }
        if(user_lrlon > LRLON) {
            pos_lrlon = (int) Math.pow(2, depth) - 1;
        } else {
            pos_lrlon = (int) ((user_lrlon - ULLON) / interval_lon);
        }
        if(user_ullat > ULLAT) {
            pos_ullat = 0;
        }
        else {
            pos_ullat = (int) ((ULLAT - user_ullat) / interval_lat);
        }
        if(user_lrlat < LRLAT) {
            pos_lrlat = (int) Math.pow(2, depth) - 1;
        } else {
            pos_lrlat = (int) ((ULLAT - user_lrlat) / interval_lat);
        }

        /* DEBUG */
        System.out.println("Depth : " + depth);
        System.out.println("pos_ullon : " + pos_ullon);
        System.out.println("pos_lrlon : " + pos_lrlon);
        System.out.println("pos_ullat : " + pos_ullat);
        System.out.println("pos_lrlat : " + pos_lrlat);


        return new int[]{pos_ullon, pos_lrlon, pos_ullat, pos_lrlat, depth};
    }

    @Override
    protected Object buildJsonResponse(Map<String, Object> result) {
        boolean rasterSuccess = validateRasteredImgParams(result);

        if (rasterSuccess) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            writeImagesToOutputStream(result, os);
            String encodedImage = Base64.getEncoder().encodeToString(os.toByteArray());
            result.put("b64_encoded_image_data", encodedImage);
        }
        return super.buildJsonResponse(result);
    }

    private Map<String, Object> queryFail() {
        Map<String, Object> results = new HashMap<>();
        results.put("render_grid", null);
        results.put("raster_ul_lon", 0);
        results.put("raster_ul_lat", 0);
        results.put("raster_lr_lon", 0);
        results.put("raster_lr_lat", 0);
        results.put("depth", 0);
        results.put("query_success", false);
        return results;
    }

    /**
     * Validates that Rasterer has returned a result that can be rendered.
     * @param rip : Parameters provided by the rasterer
     */
    private boolean validateRasteredImgParams(Map<String, Object> rip) {
        for (String p : REQUIRED_RASTER_RESULT_PARAMS) {
            if (!rip.containsKey(p)) {
                System.out.println("Your rastering result is missing the " + p + " field.");
                return false;
            }
        }
        if (rip.containsKey("query_success")) {
            boolean success = (boolean) rip.get("query_success");
            if (!success) {
                System.out.println("query_success was reported as a failure");
                return false;
            }
        }
        return true;
    }

    /**
     * Writes the images corresponding to rasteredImgParams to the output stream.
     * In Spring 2016, students had to do this on their own, but in 2017,
     * we made this into provided code since it was just a bit too low level.
     */
    private  void writeImagesToOutputStream(Map<String, Object> rasteredImageParams,
                                                  ByteArrayOutputStream os) {
        String[][] renderGrid = (String[][]) rasteredImageParams.get("render_grid");
        int numVertTiles = renderGrid.length;
        int numHorizTiles = renderGrid[0].length;

        BufferedImage img = new BufferedImage(numHorizTiles * Constants.TILE_SIZE,
                numVertTiles * Constants.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics graphic = img.getGraphics();
        int x = 0, y = 0;

        for (int r = 0; r < numVertTiles; r += 1) {
            for (int c = 0; c < numHorizTiles; c += 1) {
                graphic.drawImage(getImage(Constants.IMG_ROOT + renderGrid[r][c]), x, y, null);
                x += Constants.TILE_SIZE;
                if (x >= img.getWidth()) {
                    x = 0;
                    y += Constants.TILE_SIZE;
                }
            }
        }

        /* If there is a route, draw it. */
        double ullon = (double) rasteredImageParams.get("raster_ul_lon"); //tiles.get(0).ulp;
        double ullat = (double) rasteredImageParams.get("raster_ul_lat"); //tiles.get(0).ulp;
        double lrlon = (double) rasteredImageParams.get("raster_lr_lon"); //tiles.get(0).ulp;
        double lrlat = (double) rasteredImageParams.get("raster_lr_lat"); //tiles.get(0).ulp;

        final double wdpp = (lrlon - ullon) / img.getWidth();
        final double hdpp = (ullat - lrlat) / img.getHeight();
        AugmentedStreetMapGraph graph = SEMANTIC_STREET_GRAPH;
        List<Long> route = ROUTE_LIST;

        if (route != null && !route.isEmpty()) {
            Graphics2D g2d = (Graphics2D) graphic;
            g2d.setColor(Constants.ROUTE_STROKE_COLOR);
            g2d.setStroke(new BasicStroke(Constants.ROUTE_STROKE_WIDTH_PX,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            route.stream().reduce((v, w) -> {
                g2d.drawLine((int) ((graph.lon(v) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(v)) * (1 / hdpp)),
                        (int) ((graph.lon(w) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(w)) * (1 / hdpp)));
                return w;
            });
        }

        rasteredImageParams.put("raster_width", img.getWidth());
        rasteredImageParams.put("raster_height", img.getHeight());

        try {
            ImageIO.write(img, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private BufferedImage getImage(String imgPath) {
        BufferedImage tileImg = null;
        if (tileImg == null) {
            try {
                File in = new File(imgPath);
                tileImg = ImageIO.read(in);
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return tileImg;
    }
}
