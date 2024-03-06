package deque;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class MaxArrayDequeTest {

    @Test
    public void compareTest() {
        ValueComparator c1 = new ValueComparator();
        LengthComparator c2 = new LengthComparator();

        MaxArrayDeque<Integer> lst1 = new MaxArrayDeque<>(c1);
        MaxArrayDeque<String> lst2 = new MaxArrayDeque<>(c2);

        //lst2.addFirst("a");
        //lst2.addFirst("aa");
        //lst2.addFirst("aaaaa");
        //lst2.addFirst("aaa");
        //lst2.addFirst("aaaa");
        String s = lst2.max();
        String m = lst2.max(c2);
        System.out.println(m);



        lst1.addFirst(1);
        lst1.addFirst(1);
        //lst1.addFirst(5);
        //lst1.addFirst(88);
        //lst1.addFirst(2);
        int k = lst1.max(c1);
        System.out.println("k is " + k);
    }
}
