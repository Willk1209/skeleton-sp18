package synthesizer;

import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @auther Your Name
 */

public class TestArrayRingBuffer {

    @Test
    public void testEnqueueDequeue() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        assertEquals(1, (int) arb.dequeue());
        assertEquals(2, (int) arb.dequeue());
        arb.enqueue(4);
        arb.enqueue(5);
        assertEquals(3, (int) arb.dequeue());
        assertEquals(4, (int) arb.dequeue());
        assertEquals(5, (int) arb.peek());
        assertEquals(5, (int) arb.dequeue());
        assertTrue(arb.isEmpty());
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
}
