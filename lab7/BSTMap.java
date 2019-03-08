import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private Node root;

    private class Node {
        private K key;
        private V value;
        private int size;
        private Node left, right;


        Node(K key, V value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }

    public void clear() {
        root = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        return get(key) != null;
    }
    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        return getHelper(key, root);
    }
    // Helper Method for get //
    private V getHelper(K key, Node node) {
        if (node == null) {
            return null;
        } else {
            int compare = key.compareTo(node.key);
            if (compare < 0) {
                return getHelper(key, node.left);
            } else if (compare > 0) {
                return getHelper(key, node.right);
            } else {
                return node.value;
            }
        }
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        if (this.root == null) {
            return 0;
        } else {
            return this.root.size;
        }
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        this.root = putHelper(key, value, this.root);
    }

    private Node putHelper(K key, V value, Node node) {
        if (node == null) {
            return new Node(key, value, 1);
        } else {
            int compare = key.compareTo(node.key);
            if (compare < 0) {
                node.size += 1;
                node.left = putHelper(key, value, node.left);
            } else if (compare > 0) {
                node.size += 1;
                node.right = putHelper(key, value, node.right);
            } else {
                node.value = value;
            }
        }
        return node;
    }

    public void printInOrder(Node node) {
        printInOrder(this.root.left);
        System.out.println("Key : " + this.root.key.toString() + " | Value : " + this.root.value.toString());
        printInOrder(this.root.right);
    }

    /* Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }
}
