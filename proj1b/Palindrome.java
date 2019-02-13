public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        /** Iterative Solution **
         Deque deque = wordToDeque(word);
         while (deque.size() > 1) {
            if (deque.removeFirst() != deque.removeLast()) {
                return false;
            }
        }
        return true;
         **/
        Deque deque = wordToDeque(word);
        return isPalindromeHelper(deque);
    }

    private boolean isPalindromeHelper(Deque deque) {
        if (deque.size() <= 1) {
            return true;
        } else if (deque.removeFirst() != deque.removeLast()) {
            return false;
        } else {
            return isPalindromeHelper(deque);
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque deque = wordToDeque(word);
        while (deque.size() > 1) {
            if (!cc.equalChars((char) deque.removeFirst(), (char) deque.removeLast())) {
                return false;
            }
        }
        return true;
    }
}
