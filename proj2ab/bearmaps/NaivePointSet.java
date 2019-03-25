package bearmaps;
import java.util.ArrayList;
import java.util.List;

//@Source : Josh Hug's Pseudocode
public class NaivePointSet implements PointSet {
    private ArrayList<Point> pointSet;

    public NaivePointSet(List<Point> points) {
        pointSet = new ArrayList<>();
        for (Point point: points) {
            pointSet.add(point);
        }
    }

    @Override
    public Point nearest(double x, double y) {
        double bestDistance = Double.POSITIVE_INFINITY;
        Point bestPoint = new Point(0, 0);
        Point compDistance = new Point(x, y);

        for (Point point: pointSet) {
            Double currDistance = Point.distance(point, compDistance);
            if (currDistance < bestDistance) {
                bestDistance = currDistance;
                bestPoint = point;
            }
        }
        return bestPoint;
    }

    public static void main(String[] args) {
        Point pp1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point pp2 = new Point(3.3, 4.4);
        Point pp3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(pp1, pp2, pp3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        System.out.println(ret.getX()); // evaluates to 3.3
        System.out.println(ret.getY()); // evaluates to 4.4

        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        NaivePointSet naive = new NaivePointSet(List.of(p1, p2, p3, p4, p5, p6, p7));

        ret = naive.nearest(0.0, 7.0);
        System.out.println(ret.getX()); // evaluates to 1.0
        System.out.println(ret.getY()); // evaluates to 5.0
    }

}
