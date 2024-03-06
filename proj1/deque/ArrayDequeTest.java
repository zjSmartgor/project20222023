package deque;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {
    @Test
    public void addAndGetTest() {
        ArrayDeque<String> lst1 = new ArrayDeque<>();
        lst1.addFirst("a");
        assertEquals("wrong index can not get the output",null, lst1.get(2));
        assertEquals("expect to get a in get(0)", "a", lst1.get(0));
        lst1.removeFirst();
        lst1.printDeque();
        assertTrue("there is a inside the list and it is not empty", lst1.isEmpty());
    }

    @Test
    public void removeTest1() {
        ArrayDeque<String> lst1 = new ArrayDeque<>();
        lst1.addFirst("a");
        lst1.addFirst("b");

        assertEquals("a",lst1.removeLast());
        assertEquals("b",lst1.removeLast());

        lst1.addFirst("c");
        lst1.addFirst("d");
        assertEquals("d",lst1.removeFirst());
    }

    @Test
    public void printDequeTest() {
        ArrayDeque<String> lst1 = new ArrayDeque<>();
        lst1.addFirst("a");
        lst1.addFirst("b");
        lst1.addLast("c");
        lst1.addFirst("k");
        lst1.printDeque();


    }


    @Test
    public void addFirstTest() {
        ArrayDeque<String> lst1 = new ArrayDeque<>();
        lst1.addFirst("d");
        lst1.addFirst("c");
        lst1.addFirst("b");
        lst1.addFirst("a");
        lst1.printDeque();
        lst1.removeLast();
        lst1.printDeque();
        lst1.addLast("m");
        lst1.printDeque();


    }

    @Test
    public void multipleAddAndRemoveTest() {
        ArrayDeque<Integer> lst1 = new ArrayDeque<>();
        for (int i = 0; i <= 100; i++) {
            lst1.addFirst(i);
        }
        for (int i = 0; i < 99; i++) {
            lst1.removeLast();
        }
        lst1.printDeque();
    }

    @Test
    public void getTest() {
        ArrayDeque<Integer> lst1 = new ArrayDeque<>();
        lst1.addFirst(2);
        lst1.addLast(8);
        lst1.addFirst(1);
        lst1.removeFirst();
        lst1.removeFirst();
        System.out.print(lst1.get(0));
    }

    @Test
    public void equalObjectTest() {
        ArrayDeque<Integer> lst1 = new ArrayDeque<>();
        ArrayDeque<Integer> lst2 = new ArrayDeque<>();
        lst1.addFirst(1);
        lst1.addFirst(1);
        lst1.addFirst(1);
        lst2.addLast(1);
        lst2.addLast(1);
        lst2.addLast(1);
        assertTrue(lst1.equals(lst2));
    }

    @Test
    public void removeEmptyTest() {
        ArrayDeque<Integer> lst1 = new ArrayDeque<>();
        lst1.addLast(1);
        lst1.addLast(1);
        lst1.printDeque();
        System.out.print(lst1.removeFirst());
        System.out.println();
        lst1.printDeque();
        lst1.removeFirst();

    }
}