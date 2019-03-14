import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Collections;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private ArrayList<Node> bins;
    private double loadFactor;
    private int size;

    private class Node<K, V> {
        private K key;
        private V value;
        Node<K, V> next;

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        private Node find(K k) {
            if (this == null) {
                return null;
            } else if (key.equals(k)) {
                return this;
            } else {
                if (next != null) {
                    return next.find(k);
                } else {
                    return null;
                }
            }
        }
        private void setValue(V v) {
            this.value = v;
        }
        private V getValue() {
            return value;
        }
        private K getKey() {
            return key;
        }
        private void insert(K k, V v) {
            if (next == null) {
                next = new Node<>(k, v, null);
            } else {
                next.insert(k, v);
            }
        }
    }
    /** Constructor */
    public MyHashMap() {
        this.bins = new ArrayList<>(16);
        this.bins.addAll(Collections.nCopies(16, null));
        this.loadFactor = 0.75;
    }

    public MyHashMap(int initialSize) {
        this.bins = new ArrayList<>(initialSize);
        this.bins.addAll(Collections.nCopies(initialSize, null));
        this.loadFactor = 0.75;
    }

    public MyHashMap(int initialSize, double loadFactor) {
        this.bins = new ArrayList<>(initialSize);
        this.bins.addAll(Collections.nCopies(initialSize, null));
        this.loadFactor = loadFactor;
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        MyHashMap<K, V> temp = new MyHashMap<>(bins.size());
        bins = temp.bins;
        size = temp.size;
    }

    private int hash(K key) {
        if (key == null) {
            return 0;
        } else {
            return  (0x7fffffff & key.hashCode()) % bins.size();
        }
    }
    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        int hashKey = hash(key);
        if (bins.get(hashKey) != null) {
            return (V) bins.get(hashKey).find(key) != null;
        } else {
            return false;
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int hashKey = hash(key);
        if (bins.get(hashKey) != null) {
            return (V) bins.get(hashKey).find(key).getValue();
        } else {
            return null;
        }
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            return;
        } else {
            int hashKey = hash(key);
            if (bins.get(hashKey) == null) {
                bins.set(hashKey, new Node(key, value, null));
                size++;
            } else {
                if (this.containsKey(key)) {
                    bins.get(hashKey).find(key).setValue(value);
                } else {
                    bins.get(hashKey).insert(key, value);
                    size++;
                }
            }

            if (size / bins.size() > loadFactor) {
                resize();
            }
        }
    }


    private void resize() {
        MyHashMap<K, V> temp = new MyHashMap<>(bins.size() * 2, loadFactor);
        for (Node bin : bins) {
            for (Node e = bin; e != null; e = e.next) {
                temp.put((K) e.getKey(), (V) e.getValue());
            }
        }
        this.bins = temp.bins;
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (Node bin : bins) {
            for (Node e = bin; e != null; e = e.next) {
                keys.add((K) e.getKey());
            }
        }
        return keys;
    }


    @Override
    public Iterator<K> iterator() {
        HashSet<K> iter = new HashSet<>();
        for (Node bin : bins) {
            for (Node e = bin; bin != null; e = e.next) {
                iter.add((K) e.getKey());
            }
        }
        return iter.iterator();
    }
    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }
}
