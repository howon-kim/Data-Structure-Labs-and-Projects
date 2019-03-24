package bearmaps;

import java.util.Comparator;
import java.util.List;

 /**
 @Source : Josh Hug's Pseudocode
 **/

public class KDTree implements PointSet {
    private Node root;
    private final static boolean HORIZONTAL = true;
    private final static boolean VERTICAL = false;

    private class Node {
        private Point point;
        private boolean orientation;
        private Node left, right;

        public Node(Point p, boolean o) {
            point = p;
            orientation = o;
        }

    }

    private double pointComparator(Point i, Point j, boolean o) {
        if (o == HORIZONTAL) {
            return Double.compare(i.getX(), j.getX());
        } else {
            return Double.compare(i.getY(), j.getY());
        }
    }

    public KDTree(List<Point> points) {
        for (Point p : points) {
            put(p, HORIZONTAL);
        }
    }

    public void put(Point p, boolean o) {
        root = putHelper(p, o, root); //DEFUALt HORIZONTAL
    }

    private Node putHelper(Point p, boolean o, Node n) {
        if (n == null) {
            return new Node(p, o);
        } else {
            double compare = 0;
            if (p.getX() == n.point.getX() && p.getY() == n.point.getY()) {
                return n;
            }
            if (n.orientation == HORIZONTAL) {
                compare = Double.compare(n.point.getX(), p.getX());
            } else {
                compare = Double.compare(n.point.getY(), p.getY());
            }

            if (compare < 0) {
                n.right = putHelper(p, !o, n.right);
            } else if (compare > 0) {
                n.left = putHelper(p, !o, n.left);
            } else {
                n.right = putHelper(p, !o, n.right);
            }
        }
        return n;
    }

    @Override
    public Point nearest(double x, double y) {
        return nearest(root, new Point(x, y), root).point;
    }

    private Node nearest(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }
        if (Point.distance(n.point, goal) < Point.distance(best.point, goal)) {
            best = n;
        }
        Node goodSide = null; Node badSide = null;
        if (pointComparator(n.point, goal, n.orientation) > 0) {
            goodSide = n.left;
            badSide = n.right;
        } else {
            goodSide = n.right;
            badSide = n.left;
        }
        best = nearest(goodSide, goal, best);
        if (goodSide != null && badSide != null && Double.compare(Math.sqrt(Point.distance(best.point, goal)), Math.sqrt(Point.distance(badSide.point, goal))) >= 0) {
            best = nearest(badSide, goal, best); // Prune
        }
        return best;
    }

    public static void main(String args[]) {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
    }
}
