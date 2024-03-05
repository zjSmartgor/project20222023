package deque;
import java.util.Comparator;

public class ValueComparator implements Comparator<Integer> {
    public ValueComparator() {

    }
    public int compare(Integer a, Integer b) {
        if (a > b) {
            return 1;
        } else if (a < b) {
            return -1;
        }
        return 0;
    }



}
