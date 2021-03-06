public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 3;
        nextLast = 4;
    }

    public ArrayDeque(ArrayDeque other) {
        size = other.size;
        nextFirst = other.nextFirst;
        nextLast = other.nextLast;
        items = (T[]) new Object[other.items.length];
        for (int i = 0; i < items.length; i++) {
            items[i] = (T) other.items[i];
        }
    }

    public void addFirst(T item) {
        if (items[nextFirst] != null) {
            expandArray();
        }
        items[nextFirst] = item;
        size++;
        nextFirst--;
        if (nextFirst < 0) {
            nextFirst = items.length - 1;
        }
    }

    public void addLast(T item) {
        if (items[nextLast] != null) {
            expandArray();
        }
        items[nextLast] = item;
        size++;
        nextLast++;
        if (nextLast == items.length) {
            nextLast = 0;
        }
    }

    public T removeFirst() {
        if (size <= 0) {
            return null;
        } else {
            T temp;
            if (nextFirst + 1 == items.length) {
                nextFirst = 0;
                temp = items[nextFirst];
                items[nextFirst] = null;
            } else {
                nextFirst++;
                temp = items[nextFirst];
                items[nextFirst] = null;
            }
            size--;
            if ((double) size / items.length < 0.25 && items.length >= 16) {
                shrinkArray();
            }
            return temp;
        }
    }

    public T removeLast() {
        if (size <= 0) {
            return null;
        } else {
            T temp;
            if (nextLast - 1 < 0) {
                nextLast = items.length - 1;
                temp = items[nextLast];
                items[nextLast] = null;
            } else {
                nextLast--;
                temp = items[nextLast];
                items[nextLast] = null;
            }
            size--;
            if ((double) size / items.length < 0.25 && items.length >= 16) {
                shrinkArray();
            }
            return temp;
        }
    }

    private void expandArray() {
        int newSize = size * 2;
        T[] newArray = (T[]) new Object[newSize];
        T[] temp = iterateArray();
        int newIndex = (int) size / 2;
        nextFirst = newIndex - 1;

        for (int i = 0; i < size; i++) {
            newArray[newIndex] = temp[i];
            newIndex++;
        }
        nextLast = newIndex;
        items = newArray;
    }

    private void shrinkArray() {
        int newSize = (int) items.length / 2;
        T[] newArray = (T[]) new Object[newSize];
        T[] temp = iterateArray();
        int newIndex = (int) newSize / 4;
        nextFirst = newIndex - 1;

        for (int i = 0; i < size; i++) {
            newArray[newIndex] = temp[i];
            newIndex++;
        }
        nextLast = newIndex;
        items = newArray;
    }

    private T[] iterateArray() {
        int first = nextFirst + 1;
        int curSize = this.size;
        int index = 0;
        T[] newArray = (T[]) new Object[curSize];

        while (curSize > 0) {
            if (first >= items.length) {
                first = 0;
            } else if (items[first] != null) {
                newArray[index] = items[first];
                curSize--;
                first++;
                index++;
            } else {
                first++;
            }
        }
        return newArray;
    }

    public void printDeque() {
        T[] temp = iterateArray();
        for (int i = 0; i < size; i++) {
            System.out.print(temp[i].toString() + " ");
        }
        System.out.println();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T get(int index) {
        if (index > size) {
            return null;
        } else if (index + (nextFirst + 1) < items.length) {
            return items[index + (nextFirst + 1)];
        } else {
            return items[index - (items.length - (nextFirst + 1))];
        }
    }
}
