import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testAddFirst() {
        StudentArrayDeque<Integer> studentDeque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solutionDeque = new ArrayDequeSolution<>();

        studentDeque.addFirst(1);
        solutionDeque.addFirst(1);
        assertEquals(studentDeque.removeFirst(), solutionDeque.removeFirst());
    }
}
