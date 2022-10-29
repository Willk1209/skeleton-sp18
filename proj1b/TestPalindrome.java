import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {

    static Palindrome palindrome = new Palindrome();

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
        String TestStr = "rapper";
        assertFalse(palindrome.isPalindrome(TestStr));
        String TestStr_2 = "Cattac";
        assertFalse(palindrome.isPalindrome(TestStr_2));

        String TestStr_3 = "!";
        assertTrue(palindrome.isPalindrome(TestStr_3));
        String TestStr_4 = "RACECAR";
        assertTrue(palindrome.isPalindrome(TestStr_4));
    }

    static CharacterComparator cc = new OffByOne();
    @Test
    public void testNewIsPalindrome() {
        String TestStr = "aaa";
        assertFalse(palindrome.isPalindrome(TestStr, cc));
        String TestStr_2 = "flake";
        assertTrue(palindrome.isPalindrome(TestStr_2, cc));

        String TestStr_3 = "5!6";
        assertTrue(palindrome.isPalindrome(TestStr_3, cc));
        String TestStr_4 = "";
        assertTrue(palindrome.isPalindrome(TestStr_4, cc));
    }
}