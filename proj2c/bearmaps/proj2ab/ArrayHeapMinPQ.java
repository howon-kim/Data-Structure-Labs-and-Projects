package bearmaps.proj2ab;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

// @Source : NaiveMinPQ.java and MinPQ.java

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<PriorityNode> items;
    private HashMap<T, Integer> itemList;
    private int size;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        items.add(null);
        itemList = new HashMap<>();
        size = 0;
    }

    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        PriorityNode(T i, double p) {
            item = i;
            priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            } else {
                // return ((PriorityNode) o).getItem().equals(getItem());
                return ((PriorityNode) o).hashCode() == hashCode();
            }
        }

        @Override
        public int hashCode() {
            return getItem().hashCode();
        }
    }
    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentExceptionb if item is already present.
     * You may assume that item is never null. */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        } else {
            itemList.put(item, ++size);
            items.add(new PriorityNode(item, priority));
            swim(size);
        }
    }
    /* Returns true if the PQ contains the given item. */
    @Override
    public boolean contains(T item) {
        return itemList.containsKey(item);
    }
    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T getSmallest() {
        if (size <= 0) {
            throw new NoSuchElementException();
        } else {
            return items.get(1).getItem();
        }
    }
    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        } else {
            T key = getSmallest();
            itemList.remove(key);
            swap(1, size);
            items.remove(size--);
            sink(1);
            return key;
        }
    }


    /* Returns the number of items in the PQ. */
    @Override
    public int size() {
        return size;
    }

    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    @Override
    public void changePriority(T item, double priority) {
        if (!itemList.containsKey(item)) {
            throw new NoSuchElementException();
        } else {
            int index = itemList.get(item).intValue();
            double diff = items.get(index).getPriority() - priority;
            items.get(index).setPriority(priority);
            if (diff == 0) {
                return;
            } else if (diff < 0) {
                sink(index);
            } else {
                swim(index);
            }
        }
    }

    /** Helper Function **/
    private void swim(int i) {
        while (i > 1 && greater(i / 2, i)) {
            swap(i / 2, i);
            i = i / 2;
        }
    }
    private void sink(int i) {
        while (2 * i <= size) {
            int j = 2 * i;
            if (j < size && greater(j, j + 1)) {
                j++;
            }
            if (!greater(i, j)) {
                return;
            }
            swap(i, j);
            i = j;
        }
    }
    private boolean greater(int i, int j) {
        // If j is smaller than i, its positive.
        return items.get(i).compareTo(items.get(j)) > 0;
    }

    private void swap(int i, int j) {
        PriorityNode temp = items.get(i);
        items.set(i, items.get(j));
        items.set(j, temp);
        //DOUBLE CHECK
        itemList.replace(items.get(i).getItem(), i);
        itemList.replace(items.get(j).getItem(), j);
    }

    public static void main(String []args) {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ();
        test.add('a', 3);
        test.add('b', 1);
        test.removeSmallest();
    }
}
