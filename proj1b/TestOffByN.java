import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static CharacterComparator ccN = new OffByN(4);

    @Test
    public void TestEqualChars(){
        assertEquals(ccN.equalChars('x','x'),false);
        assertEquals(ccN.equalChars('s','w'),true);
        assertEquals(ccN.equalChars('2','6'),true);

    }
}
