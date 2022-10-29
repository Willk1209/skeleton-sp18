/** This class outputs all palindromes in the words file in the current directory. */
public class PalindromeFinder {
//    /*
//    static CharacterComparator cc = new OffByN(1); // if you want to test offByOne version.
    public static void main(String[] args) {
        int minLength = 4;
        In in = new In("../library-sp18/data/words.txt");
        Palindrome palindrome = new Palindrome();

        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() >= minLength && palindrome.isPalindrome(word)) {
                System.out.println(word);
            }
        }
    }
//    Uncomment this class once you've written isPalindrome. */
}
