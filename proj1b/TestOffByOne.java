import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void TestEqualChars(){
        assertEquals(offByOne.equalChars('x','Y'),false);
        assertEquals(offByOne.equalChars('a','a'),false);
        assertEquals(offByOne.equalChars('2','3'),true);
        assertEquals(offByOne.equalChars('s','r'),true);
    }
}
