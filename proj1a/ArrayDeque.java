public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque(){
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    public ArrayDeque(ArrayDeque other) {
        size = other.size;
        nextFirst = other.nextFirst;
        nextLast = other.nextLast;
        items = (T[]) new Object[other.items.length];
        for(int i = 0; i < items.length; i++){
            items[i] = (T) other.items[i];
        }
    }

    /* Adds an item of type T to the front of the deque */
    public void addFirst(T item){
        if(items[nextFirst] != null){
            expandArray();
        }
        items[nextFirst] = item;
        size++;
        nextFirst--;
        if(nextFirst < 0){
            nextFirst = items.length - 1;
        }
    }
    public void addLast(T item){
        if(items[nextLast] != null){
            expandArray();
        }
        items[nextLast] = item;
        size++;
        nextLast++;
        if(nextLast == items.length){
            nextLast = 0;
        }
    }

    private void expandArray() {
        T[] newArray = (T[]) new Object[size * 2];
        T[] temp = iterateArray();
        int newIndex = (int) size / 2;
        nextFirst = newIndex - 1;

        for(int i = 0 ; i < size ; i++){
            newArray[newIndex] = temp[i];
            newIndex++;
        }

        nextLast = newIndex;
        items = newArray;
    }

    private T[] iterateArray(){
        int first = nextFirst + 1;
        int size = this.size;
        int index = 0;
        T[] newArray = (T[]) new Object[size];

        while(size > 0){
            if(first >= items.length){
                first = 0;
            }
            else if(items[first] != null) {
                newArray[index] = items[first];
                size--;
                first++;
                index++;
            }
            else {
                first++;
            }
        }
        return newArray;
    }

    public void printDeque(){
        T[] temp = iterateArray();
        for (int i = 0; i < size; i++){
            System.out.print(temp[i].toString() + " ");
        }
        System.out.println();
    }

    public int size(){
        return size;
    }
    public boolean isEmpty(){
        return size == 0;
    }
    /*
    public T get(int index){
        if(index > size){
            return null;
        }
        else if(items[index + nextFirst] != null){
            return items[index + nextFirst];
        }
        else{

        }
    }*/

}
