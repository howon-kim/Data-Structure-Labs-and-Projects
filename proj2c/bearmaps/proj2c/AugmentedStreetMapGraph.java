package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.Point;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, Howon Kim
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private static Map<Point, Long> ids;
    private static Map<String, List<Point>> names; // cancel static later
    private static Map<Point, Long> allIds;
    private KDTree points;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        List<Point> data = new LinkedList<>();
        ids = new HashMap<>();
        names = new HashMap<>();
        allIds = new HashMap<>();

        for (Node node : nodes) {
            Point point = new Point(node.lon(), node.lat());
            String name = node.name();
            allIds.put(point, node.id());
            if(name != null) {
                String cleanedName = cleanString(name);
                if (names.containsKey(cleanedName)) {
                    names.get(cleanedName).add(point);
                } else {
                    List<Point> locations = new LinkedList<>();
                    locations.add(point);
                    names.put(cleanedName, locations);
                }
            }
            if(!neighbors(node.id()).isEmpty()) {
                data.add(point);
                ids.put(point, node.id());
            }
        }
        points = new KDTree(data);
    }

    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point point = points.nearest(lon, lat);
        return ids.get(point);
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        return new LinkedList<>();
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public static List<Map<String, Object>> getLocations(String locationName) {
        String name =  locationName;
        List<Map<String, Object>> result = new LinkedList<>();
        for(Point n : names.get(cleanString(locationName))) {
            Map<String, Object> temp = new HashMap<>();
            temp.put("lat",  n.getY());
            temp.put("lon", n.getX());
            temp.put("name", locationName);
            temp.put("id", allIds.get(n));
            result.add(temp);
        }
        return result;
    }

    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
