public class LinkedListDeque<T> {
    private class Node {
        public T item;
        public Node next;
        public Node prev;

        public Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
    private int size;
    private Node sentinel;

    /* Creates an empty linked list deque */
    public LinkedListDeque(){
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel.next = sentinel;
        size = 0;
    }

    /* Creates a deep copy of other */
    public LinkedListDeque(LinkedListDeque other){
        this();
        for (int i = 0; i < other.size(); i++)
            this.addLast((T) other.get(i));
        size = other.size();
    }
    /* Adds an item of type T to the front of the deque */
    public void addFirst(T item){
        Node temp = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = temp;
        sentinel.next = temp;
        size++;
    }

    /*  Adds an item of type T to the back of the deque */
    public void addLast(T item){
        Node temp = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = temp;
        sentinel.prev = temp;
        size++;
    }

    /* Returns true if deque is empty, false otherwise */
    public boolean isEmpty(){
        return size == 0;
    }

    /* Returns the number of items in the deque */
    public int size(){
        return size;
    }

    /* Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    public void printDeque(){
        Node temp = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.print(temp.item.toString() + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    /* Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    public T removeFirst(){
        T temp = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size--;
        return temp;

    }

    /* Removes and returns the item at the back of the deque.
     * If no such item exists, returns null
     */
    public T removeLast(){
        T temp = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size--;
        return temp;
    }

    /* Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     */
    public T get(int index){
        Node temp = sentinel;
        if (index >= size)
            return null;
        else{
            for (int i = 0 ; i <= index ; i++){
                temp = temp.next;
            }
            return temp.item;
        }
    }

    /* Recursive version of get */
    public T getRecursive(int index){
        if (index >= size)
            return null;
        else
            return helper(index, sentinel.next);

    }
    /* Helper function of recursive version of get */
    private T helper(int index, Node temp){
        if(index == 0)
            return temp.item;
        else
            return helper(--index, temp.next);
    }
}
