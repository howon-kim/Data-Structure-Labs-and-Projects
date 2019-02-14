import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testAddFirstRemoveFirst() {
        StudentArrayDeque<Integer> studentDeque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solutionDeque = new ArrayDequeSolution<>();
        for (int testNum = 0; testNum <= 10; testNum++) {
            String message = "";
            for (int i = 0; i <= testNum; i++) {
                Integer number = StdRandom.uniform(10);
                studentDeque.addFirst(number);
                solutionDeque.addFirst(number);
                message += "\naddFirst(" + number + ")";
            }
            for (int i = 0; i <= testNum; i++) {
                Integer actual = studentDeque.removeFirst();
                Integer expected = solutionDeque.removeFirst();
                message += "\nremoveFirst(): " + actual;
                assertEquals(expected, actual);
            }
        }
    }

    @Test
    public void testAddFirstRemoveLast() {
        StudentArrayDeque<Integer> studentDeque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solutionDeque = new ArrayDequeSolution<>();
        for (int testNum = 0; testNum <= 10; testNum++) {
            String message = "";
            for (int i = 0; i <= testNum; i++) {
                Integer number = StdRandom.uniform(10);
                studentDeque.addFirst(number);
                solutionDeque.addFirst(number);
                message += "\naddFirst(" + number + ")";
            }
            for (int i = 0; i <= testNum; i++) {
                Integer actual = studentDeque.removeLast();
                Integer expected = solutionDeque.removeLast();
                message += "\nremoveLast(): " + actual;
                assertEquals(message, expected, actual);
            }
        }
    }

    @Test
    public void testAddLastRemoveLast() {
        StudentArrayDeque<Integer> studentDeque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solutionDeque = new ArrayDequeSolution<>();
        for (int testNum = 0; testNum <= 10; testNum++) {
            String message = "";
            for (int i = 0; i <= testNum; i++) {
                Integer number = StdRandom.uniform(10);
                studentDeque.addLast(number);
                solutionDeque.addLast(number);
                message += "\naddLast(" + number + ")";
            }
            for (int i = 0; i <= testNum; i++) {
                Integer actual = studentDeque.removeLast();
                Integer expected = solutionDeque.removeLast();
                message += "\nremoveLast(): " + actual;
                assertEquals(message, expected, actual);            }
        }
    }


    @Test
    public void testAddLastRemoveFirst() {
        StudentArrayDeque<Integer> studentDeque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solutionDeque = new ArrayDequeSolution<>();
        for (int testNum = 0; testNum <= 10; testNum++) {
            String message = "";
            for (int i = 0; i <= testNum; i++) {
                Integer number = StdRandom.uniform(10);
                studentDeque.addLast(number);
                solutionDeque.addLast(number);
                message += "\naddLast(" + number + ")";
            }
            for (int i = 0; i <= testNum; i++) {
                Integer actual = studentDeque.removeFirst();
                Integer expected = solutionDeque.removeFirst();
                message += "\nremoveFirst(): " + actual;
                assertEquals(expected, actual);
            }
        }
    }
}
