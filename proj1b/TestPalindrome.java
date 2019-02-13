import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator offbyone = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("A"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("noon"));

        assertFalse(palindrome.isPalindrome("Hello"));
        assertFalse(palindrome.isPalindrome("Racecar"));
    }

    @Test
    public void testIsPalindromeOffByOne() {
        assertTrue(palindrome.isPalindrome("", offbyone));
        assertTrue(palindrome.isPalindrome("a", offbyone));
        assertTrue(palindrome.isPalindrome("aeb", offbyone));
        assertTrue(palindrome.isPalindrome("ab", offbyone));
        assertTrue(palindrome.isPalindrome("abecb", offbyone));

        assertFalse(palindrome.isPalindrome("Abecb", offbyone));
        assertFalse(palindrome.isPalindrome("ac", offbyone));

    }
}
