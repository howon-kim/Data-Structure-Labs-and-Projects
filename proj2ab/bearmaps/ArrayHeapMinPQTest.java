package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.PriorityQueue;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    @Test
    public void inclusiveTest() {
        ArrayHeapMinPQ test1 = new ArrayHeapMinPQ();
        test1.add('a', 1);
        test1.add('b', 2);
        test1.add('c', 3);
        assertEquals('a', test1.getSmallest());
        test1.changePriority('c', 0.5);
        assertEquals('c', test1.getSmallest());
        test1.removeSmallest();
        assertEquals('a', test1.getSmallest());
        test1.removeSmallest();
        assertEquals('b', test1.getSmallest());
        assertEquals(1, test1.size());
        assertTrue(test1.contains('b'));
        assertFalse(test1.contains('a'));
        assertFalse(test1.contains('c'));
        test1.add('a', 1);
        test1.add('c', 3);
        assertEquals('a', test1.removeSmallest());
        test1.changePriority('b', 4.0);
        assertEquals('c', test1.removeSmallest());
        assertEquals('b', test1.getSmallest());

        ArrayHeapMinPQ test2 = new ArrayHeapMinPQ();
        test2.add('a', -5);
        assertEquals('a', test2.removeSmallest());
        test2.add('a', -5);
        test2.changePriority('a', -5);
        test2.add('b', -4);
        assertEquals('a', test2.getSmallest());
        test2.changePriority('a', -1);
        assertEquals('b', test2.removeSmallest());
        assertEquals('a', test2.getSmallest());
        test2.add('b', 0);
        test2.add('c', 1);
        test2.add('d', 2);
        test2.changePriority('d', -10);
        test2.changePriority('a', 100);
        test2.changePriority('c', -15);
        test2.changePriority('b', -20);
        assertEquals('b', test2.getSmallest());
        assertEquals('b', test2.removeSmallest());
        assertEquals('c', test2.removeSmallest());
        assertEquals('d', test2.removeSmallest());
        test2.add("aa", -300);
        assertEquals("aa", test2.removeSmallest());
        assertEquals('a', test2.getSmallest());
    }

    @Test
    public void containSizeTest() {
        ArrayHeapMinPQ test2 = new ArrayHeapMinPQ();
        test2.add('b', 0);
        test2.add('c', 1);
        test2.add('d', 2);
        test2.changePriority('d', -10);
        test2.changePriority('c', -15);
        test2.changePriority('b', 20);
        assertEquals('c', test2.getSmallest());
        assertEquals('c', test2.removeSmallest());
        assertTrue(test2.contains('d'));
        assertEquals(2, test2.size());
        assertFalse(test2.contains('c'));
    }

    @Test
    public void addRemoveTest() {
        ArrayHeapMinPQ test1 = new ArrayHeapMinPQ();
        test1.add(1, 5);
        test1.add(2, 4);
        test1.add(3, 3);
        test1.add(4, 2);
        test1.add(5, 1);
        test1.add(6, 0);
        test1.add(7, -1);
        test1.add(8, -2);
        test1.add(9, -3);
        test1.add(10, -4);
        test1.add(11, -5);
        test1.add(12, -6);
        test1.add(13, -7);
        test1.add(14, -8);
        test1.add(15, -9);
        test1.add(16, -10);
        test1.add(17, -10);

        assertEquals(16, test1.getSmallest());
        assertEquals(16, test1.removeSmallest());
        assertEquals(17, test1.removeSmallest());
        assertEquals(15, test1.removeSmallest());
        assertEquals(14, test1.removeSmallest());
        assertEquals(13, test1.removeSmallest());
        assertEquals(12, test1.removeSmallest());
        assertEquals(11, test1.removeSmallest());
        assertEquals(10, test1.removeSmallest());
        assertEquals(9, test1.removeSmallest());
        assertEquals(8, test1.removeSmallest());
        assertEquals(7, test1.removeSmallest());
        assertEquals(6, test1.removeSmallest());
        assertEquals(5, test1.removeSmallest());
        assertEquals(4, test1.removeSmallest());
        assertEquals(3, test1.removeSmallest());
        assertEquals(2, test1.removeSmallest());
        assertEquals(1, test1.removeSmallest());


    }

    @Test
    public void priorityNodeTest() {
        ArrayHeapMinPQ test1 = new ArrayHeapMinPQ();
        ArrayHeapMinPQ test2 = new ArrayHeapMinPQ();

        test1.add("!@#123", 1);
        test2.add("!@#123", 2);

        assertTrue(test1.getSmallest().equals(test2.getSmallest()));
    }

    @Test
    public void testSpeed() {
        Stopwatch sw = new Stopwatch();
        ArrayHeapMinPQ test1 = new ArrayHeapMinPQ();
        int size = 10000000;

        for (int i = 0; i < size; i += 1) {
            test1.add(i, Math.random());
        }
        System.out.println("Total time for add : " + sw.elapsedTime() + " seconds.");

        sw = new Stopwatch();
        for (int i = 0; i < 1000; i += 1) {
            test1.changePriority(i, Math.random());
        }
        System.out.println("Total time for changePriority: " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        System.out.println("getSmalleset : " + test1.getSmallest());
        System.out.println("Total time for getSmallest : " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        System.out.println("contains : " + test1.contains(Math.random()));
        System.out.println("Total time for contains : " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        System.out.println("size : " + test1.size());
        System.out.println("Total time for size : " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        for (int i = 0; i < 1000; i += 1) {
            test1.removeSmallest();
        }
        System.out.println("Total time for removeSmallest : " + sw.elapsedTime() +  " seconds.");
    }
    @Test
    public void testNaiveSpeed() {
        Stopwatch sw = new Stopwatch();
        NaiveMinPQ test1 = new NaiveMinPQ();
        int size = 10000000;

        for (int i = 0; i < size; i += 1) {
            test1.add(i, Math.random());
        }
        System.out.println("Total time elapsed for add : " + sw.elapsedTime() + " seconds.");

        sw = new Stopwatch();
        for (int i = 0; i < 1000; i += 1) {
            test1.changePriority(i, Math.random());
        }
        System.out.println("Total time for changePriority : " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        System.out.println("getSmalleset : " + test1.getSmallest());
        System.out.println("Total time for getSmallest : " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        System.out.println("contains : " + test1.contains(Math.random()));
        System.out.println("Total time for contains : " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        System.out.println("size : " + test1.size());
        System.out.println("Total time for size : " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        for (int i = 0; i < 1000; i += 1) {
            test1.removeSmallest();
        }
        System.out.println("Total time for removeSmallest : " + sw.elapsedTime() +  " seconds.");

    }
    @Test
    public void testJavaPQSpeed() {
        Stopwatch sw = new Stopwatch();
        PriorityQueue test1 = new PriorityQueue();
        int size = 10000000;

        for (int i = 0; i < size; i += 1) {
            test1.add(Math.random());
        }
        System.out.println("Total time elapsed for add : " + sw.elapsedTime() + " seconds.");

        sw = new Stopwatch();
        System.out.println("contains : " + test1.contains(Math.random()));
        System.out.println("Total time for contains : " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        System.out.println("size : " + test1.size());
        System.out.println("Total time for size : " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        for (int i = 0; i < 1000; i += 1) {
            test1.remove();
        }
        System.out.println("Total time for removeSmallest : " + sw.elapsedTime() +  " seconds.");

    }
}
