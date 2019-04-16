package bearmaps.proj2ab;

import java.util.List;

/**
 * @Source : Josh Hug's Pseudocode
 **/

public class KDTree implements PointSet {

    private Node root;
    private static final boolean HORIZONTAL = true;
    private static final boolean VERTICAL = false;

    private class Node {
        private Point point;
        private boolean orientation;
        private Node left, right;

        Node(Point p, boolean o) {
            point = p;
            orientation = o;
            left = right = null;
        }
    }


    public KDTree(List<Point> points) {
        for (Point p : points) {
            put(p, HORIZONTAL);
        }
    }

    /**
     * PRIVATE METHOD
     **/
    private void put(Point p, boolean o) {
        root = putHelper(p, o, root);
    }

    private Node putHelper(Point p, boolean o, Node n) {
        if (n == null) {
            return new Node(p, o);
        }
        if (p.equals(n.point)) {
            return n;
        } else {
            int compare = pointComparator(n.point, p, o);
            if (compare > 0) {
                n.left = putHelper(p, !o, n.left);
            } else {
                n.right = putHelper(p, !o, n.right);
            }
        }
        return n;
    }

    private int pointComparator(Point i, Point j, boolean o) {
        if (o == HORIZONTAL) {
            return Double.compare(i.getX(), j.getX());
        } else {
            return Double.compare(i.getY(), j.getY());
        }
    }

    private Node nearest(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }
        if (Point.distance(n.point, goal) < Point.distance(best.point, goal)) {
            best = n;
        }

        Node goodSide, badSide;
        if (pointComparator(n.point, goal, n.orientation) >= 0) {
            goodSide = n.left;
            badSide = n.right;
        } else {
            goodSide = n.right;
            badSide = n.left;
        }

        best = nearest(goodSide, goal, best);

        /* PRUNING */
        if (n.orientation == HORIZONTAL) {
            Point newPoint = new Point(n.point.getX(), goal.getY());
            if (Point.distance(newPoint, goal) <= Point.distance(best.point, goal)) {
                best = nearest(badSide, goal, best);
            }
        }
        if (n.orientation == VERTICAL) {
            Point newPoint = new Point(goal.getX(), n.point.getY());
            if (Point.distance(newPoint, goal) <= Point.distance(best.point, goal)) {
                best = nearest(badSide, goal, best);
            }
        }
        return best;
    }

    @Override
    public Point nearest(double x, double y) {
        return nearest(root, new Point(x, y), root).point;
    }

    public static void main(String[] args) {
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
