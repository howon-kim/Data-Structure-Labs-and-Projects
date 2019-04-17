package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.Point;
import edu.princeton.cs.algs4.TST;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, Howon Kim
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    private static Map<String, List<Map<String, Object>>> names;
    private Map<Point, Long> ids;
    private KDTree points;
    private TST tries;


    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        List<Point> data = new LinkedList<>();
        ids = new HashMap<>();
        names = new HashMap<>();
        tries = new TST();

        for (Node node : nodes) {
            Point point = new Point(node.lon(), node.lat());
            if(node.name() != null) {
                String cleanedName = cleanString(node.name());
                Map<String, Object> temp = new HashMap<>();
                if(!cleanedName.isEmpty()){
                    tries.put(cleanedName, node);
                }
                temp.put("lat", point.getY());
                temp.put("lon", point.getX());
                temp.put("name", node.name());
                temp.put("id", node.id());
                if (names.containsKey(cleanedName)) {
                    names.get(cleanedName).add(temp);
                } else {
                    List<Map<String, Object>> locations = new LinkedList<>();
                    locations.add(temp);
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
        String cleanedName = cleanString(prefix);
        List<String> results = new LinkedList<>();
        for (Object s: tries.keysWithPrefix(cleanedName)){
            List<Map<String, Object>> temp = getLocations((String) s);
            for (int i = 0; i < temp.size(); i++) {
                results.add((String) temp.get(i).get("name"));
            }
        }
        return results;
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
        return names.get(locationName);
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
