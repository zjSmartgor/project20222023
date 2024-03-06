package deque;
import java.util.Comparator;



public class LengthComparator implements Comparator<String> {
    public LengthComparator() {
    }
    public int compare(String a, String b) {
        if (a.length() > b.length()) {
            return 1;
        } else if (a.length() < b.length()) {
            return -1;
        }
        return 0;
    }
}
