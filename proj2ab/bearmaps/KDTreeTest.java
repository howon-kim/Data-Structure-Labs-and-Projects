package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KDTreeTest {
    private static Random r = new Random();

    @Test
    public void basicTest() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        KDTree test = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        NaivePointSet naive = new NaivePointSet(List.of(p1, p2, p3, p4, p5, p6, p7));

        assertEquals(naive.nearest(3.0, 4.0), test.nearest(3.0, 4.0));
        assertEquals(new Point(1.0, 5.0), naive.nearest(0.0, 7.0));
        assertEquals(new Point(1.0, 5.0), test.nearest(0.0, 7.0));
    }

    @Test
    public void randomTest() {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            double x = r.nextDouble();
            double y = r.nextDouble();
            Point p = new Point(x, y);
            points.add(p);
        }

        KDTree test = new KDTree(points);
        NaivePointSet naive = new NaivePointSet(points);

        for (int i = 0; i < 10000; i++) {
            double x = r.nextDouble();
            double y = r.nextDouble();
            assertTrue(Point.distance(naive.nearest(x, y), test.nearest(x, y)) < .000000000001);
        }
    }

    @Test
    public void individualSpeedTest() {
        Stopwatch sw = new Stopwatch();
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            double x = r.nextDouble() * 50;
            double y = r.nextDouble() * 50;
            Point p = new Point(x, y);
            points.add(p);
        }
        System.out.println("Total time for making List : " + sw.elapsedTime() + " seconds.");

        sw = new Stopwatch();
        KDTree test = new KDTree(points);
        System.out.println("Total time for making KDTree : " + sw.elapsedTime() + " seconds.");

        sw = new Stopwatch();
        NaivePointSet naive = new NaivePointSet(points);
        System.out.println("Total time for making Naive Set : " + sw.elapsedTime() + " seconds.");

        for (int i = 0; i < 10000; i++) {
            double x = r.nextDouble() * 50;
            double y = r.nextDouble() * 50;
            sw = new Stopwatch();
            naive.nearest(x, y);
            double naiveTime =  sw.elapsedTime();

            sw = new Stopwatch();
            test.nearest(x, y);
            double kdTime =  sw.elapsedTime();

            //System.out.println(kdTime / naiveTime);

        }
    }

    @Test
    public void cummulativeSpeedTest() {
        Stopwatch sw = new Stopwatch();
        List<Point> points = new ArrayList<>();
        List<Point> query = new ArrayList<>();

        for (int i = 0; i < 100000; i++) {
            double x = r.nextDouble() * 100;
            double y = r.nextDouble() * 100;
            Point p = new Point(x, y);
            points.add(p);
        }
        System.out.println("Total time for making List : " + sw.elapsedTime() + " seconds.");

        sw = new Stopwatch();
        KDTree test = new KDTree(points);
        System.out.println("Total time for making KDTree : " + sw.elapsedTime() + " seconds.");

        sw = new Stopwatch();
        NaivePointSet naive = new NaivePointSet(points);
        System.out.println("Total time for making Naive Set : " + sw.elapsedTime() + " seconds.");

        int queryTimes = 10000;
        for (int i = 0; i < queryTimes; i++) {
            double x = r.nextDouble() * 100;
            double y = r.nextDouble() * 100;
            query.add(new Point(x, y));
            assertTrue(Point.distance(naive.nearest(x, y), test.nearest(x, y)) < .000000000001);
        }

        sw = new Stopwatch();
        for (Point p : query) {
            naive.nearest(p.getX(), p.getY());
        }
        System.out.println("nearest (Naive Set) : " + sw.elapsedTime() + " seconds.");

        sw = new Stopwatch();
        for (Point p : query) {
            test.nearest(p.getX(), p.getY());
        }
        System.out.println("nearest (KD Tree) : " + sw.elapsedTime() + " seconds.");
    }
}
