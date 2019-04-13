import edu.princeton.cs.algs4.Queue;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(3);
        queue.enqueue(2);
        queue.enqueue(9);
        queue.enqueue(5);
        Queue result = QuickSort.quickSort(queue);
        System.out.println(queue);
        Integer first = (Integer) result.dequeue();
        while(!result.isEmpty()){
            Integer second = (Integer) result.dequeue();
            assertTrue(first.compareTo(second) <= 0);
            first = second;
        }
    }

    @Test
    public void testMergeSort() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(6);
        queue.enqueue(2);
        queue.enqueue(3);
        Queue result = MergeSort.mergeSort(queue);
        Integer first = (Integer) result.dequeue();
        while(!result.isEmpty()){
            Integer second = (Integer) result.dequeue();
            assertTrue(first.compareTo(second) <= 0);
            first = second;
        }
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
