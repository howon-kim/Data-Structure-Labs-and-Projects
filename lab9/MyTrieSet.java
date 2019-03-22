import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MyTrieSet implements TrieSet61B {

    Node root;

    private static class Node {
        private char c;
        private boolean isKey;
        private HashMap<Character, Node> children;

        public Node() {
            isKey = false;
            children = new HashMap<>();
        }
    }

    public MyTrieSet(){
        clear();
    }

    /** Clears all items out of Trie */
    @Override
    public void clear() {
        root = new Node();
    }

    /** Returns true if the Trie contains KEY, false otherwise */
    @Override
    public boolean contains(String key) {
        if (key == null || key.length() < 1) {
            return false;
        } else {
            Node curr = root;
            for (int i = 0; i < key.length(); i++) {
                char c = key.charAt(i);
                if (curr == null) {
                    return false;
                } else {
                    curr = curr.children.get(c);
                }
            }
            return curr.isKey;
        }
    }

    /** Inserts string KEY into Trie */
    @Override
    public void add(String key) {
        add(root, key);
    }

    private void add(Node curr, String key) {
        if (key.isEmpty()) {
            curr.isKey = true;
            return;
        }

        char c = key.charAt(0);
        String rest = key.substring(1);

        Node newNode = curr.children.get(c);
        if (newNode == null) {
            newNode = new Node();
            newNode.c = c;
            curr.children.put(c, newNode);
        }
        add(newNode, rest);
    }

    /** Returns a list of all words that start with PREFIX */
    @Override
    public List<String> keysWithPrefix(String prefix) {
        ArrayList<String> results = new ArrayList<>();
        Node curr = root;
        for (int i = 0; i < prefix.length(); i++) {
            curr = curr.children.get(prefix.charAt(i));
            if (curr == null) {
                return new ArrayList<>();
            }
        }

        collectionHelper(curr, prefix, results);
        return results;
    }

    private void collectionHelper(Node n, String prefix, List<String> results) {
        if (n.isKey) {
            results.add(prefix);
        }

        Set<Character> keys = n.children.keySet();
        for (Character c: keys) {
            Node newNode = n.children.get(c);
            collectionHelper(newNode, prefix + newNode.c, results);
        }
    }
    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public String longestPrefixOf(String key) {
        Node curr = root;
        String prefix = "";

        for (int i = 0; i < key.length(); i++) {
            Node newNode = curr.children.get(key.charAt(i));
            if (newNode == null) {
                break;
            } else {
                prefix += key.charAt(i);
            }
        }
        return prefix;
    }
}
