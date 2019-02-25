package es.datastructur.synthesizer;
import java.util.Arrays;
import java.util.Iterator;

//TODO: Make sure to that this class and all of its methods are public
//TODO: Make sure to add the override tag for all overridden methods
//TODO: Make sure to make this class implement BoundedQueue<T>

public class ArrayRingBuffer<T> implements BoundedQueue<T>{
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    @Override
    public Iterator<T> iterator(){
        return new ArrayBufferIterator();
    }

    private class ArrayBufferIterator implements Iterator<T> {
        private int position;
        private int count;

        public ArrayBufferIterator() {
            position = first;
            count = 0;
        }
        public boolean hasNext() {
            return count < fillCount();
        }
        public T next() {
            T returnItem = rb[position % capacity()];
            position++;
            count++;
            return returnItem;
        }

    }
    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        rb = (T[]) new Object[capacity];
        Arrays.fill(rb, 0.0);
        first = last = fillCount = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */

    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update
        //       last.
        if(fillCount() == capacity()) {
            throw new RuntimeException("Ring Buffer overflow");
        }
        rb[last] = x;
        last++;
        if (last == rb.length) {
            last = 0;
        }
        fillCount++;
        return;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and
        //       update first.
        if(fillCount() == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        T oldest = rb[first];
        first++;
        if (first == rb.length) {
            first = 0;
        }
        fillCount--;
        return oldest;
        //return null;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should
        //       change.
        if (fillCount() == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        return rb[first];
        //return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass().getName() != this.getClass().getName()) {
            return false;
        }

        ArrayRingBuffer<T> compare = (ArrayRingBuffer) o;
        Iterator<T> iter = this.iterator();

        if (compare.fillCount() != this.fillCount()) {
            return false;
        }

        for (T x : compare){
            if (x != iter.next()){
                return false;
            }
        }
        return true;
    }

    // TODO: When you get to part 4, implement the needed code to support
    //       iteration and equals.
}
    // TODO: Remove all comments that say TODO when you're done.
