package deque;
import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>   {
    private Comparator<T> importedCompartor;


    public MaxArrayDeque(Comparator<T> c) {
        super();
        importedCompartor = c;
    }
    public T max() {
        if (this.size() == 0) {
            return null;
        } else if (this.size() == 1) {
            return this.get(0);
        } else {
            int idx = 0;
            for (int i = 0; i < this.size(); i++) {
                if (importedCompartor.compare(this.get(i), this.get(idx)) >= 0) {
                    idx = i;
                }
            }
            return this.get(idx);
        }

    }
    public T max(Comparator<T> c) {
        if (this.size() == 0) {
            return null;
        } else if (this.size() == 1) {
            return this.get(0);
        } else {
            int idx = 0;
            for (int i = 0; i < this.size(); i++) {
                if (c.compare(this.get(i), this.get(idx)) >= 0) {
                    idx = i;
                }
            }
            return this.get(idx);
        }
    }
}
