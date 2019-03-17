package bearmaps;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    @Test
    public void basicTest() {
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
    }
}
