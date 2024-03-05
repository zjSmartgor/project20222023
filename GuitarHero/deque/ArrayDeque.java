package deque;
import java.util.Iterator;
public class ArrayDeque<T> implements Deque<T>, Iterable<T> {

    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private static final int INITIALSIZE = 8;
    private static final int REDUCESIZEFACTOR = 4;
    private static final int SIZEFIXED = 16;

    /** constructor for arrayDeque, starting length 8
     * My initiation is the "last part" starts from index 0 to increase
     * and the "first part" start from index of items.length -1 to decrease */
    public ArrayDeque() {
        items = (T[]) new Object[INITIALSIZE];
        size = 0;
        nextFirst = items.length - 1;
        nextLast = 0;

    }

    /** *
     * we should care about some details in this func
     * 1.For me, I try to extend the length of items to two times as before
     * 2.how to copy the "first part" and the "last part" into new items
     * 3.reassign nextFirst and nextLast
     * 4.still need to care more on the position of nextFirst and nextLast in enlarge and reduce size situation.
     */

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        int sizeOfFirst;
        int sizeOfLast;
        /** consider the situation that we need to reduce the size and nextFirst is smaller than nextLast*/
        if (capacity < items.length && nextFirst < nextLast) {
            sizeOfFirst = 0;
            sizeOfLast = size;
            /** just have the "Last part" on the left and no "first part" on the right*/
            System.arraycopy(items, nextFirst + 1, a, 0, sizeOfLast);
            nextLast = sizeOfLast;
        /** consider the situation that we need to enlarge the list and nextFirst is larger than nextLast*/
        } else if (capacity > items.length && nextFirst > nextLast) {
            sizeOfFirst = 0;
            sizeOfLast = size;
            System.arraycopy(items, 0, a, 0, sizeOfLast);
            nextLast = sizeOfLast;

        } else {
            sizeOfFirst = items.length - nextFirst - 1;
            sizeOfLast = nextLast;
            /** copy the "Last part" to the left side of a*/
            System.arraycopy(items, 0, a, 0, sizeOfLast);
            /** copy the "First part" to the right side of a */
            System.arraycopy(items, nextFirst + 1, a, capacity - sizeOfFirst,
                    sizeOfFirst);
            /** don't need to change nextLast for this case*/
        }
        /** reassign nextFist  */
        nextFirst = capacity - sizeOfFirst - 1;
        items = a;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        if (nextFirst == 0) {
            items[nextFirst] = item;
            nextFirst = items.length - 1;
            size += 1;
        } else {
            items[nextFirst] = item;
            nextFirst -= 1;
            size += 1;
        }


    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        if (nextLast == items.length - 1) {
            items[nextLast] = item;
            nextLast = 0;
            size += 1;
        } else {
            items[nextLast] = item;
            nextLast += 1;
            size += 1;
        }
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        if (nextFirst >= nextLast) {
            for (int i = nextFirst + 1; i < items.length; i++) {
                System.out.print(items[i]);
                System.out.print(" ");
            }
            for (int i = 0; i < nextLast; i++) {
                System.out.print(items[i]);
                System.out.print(" ");
            }
        } else {
            for (int i = nextFirst + 1; i < nextLast; i++) {
                System.out.print(items[i]);
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        T itemReturn;

        if (size != 0) {
            if (size >= SIZEFIXED && (size - 1) < items.length / REDUCESIZEFACTOR) {
                resize(items.length / REDUCESIZEFACTOR);
            }
            if (nextFirst == items.length - 1) {
                itemReturn = items[0];
                items[0] = null;
                nextFirst = 0;
            } else {
                itemReturn = items[nextFirst + 1];
                items[nextFirst + 1] = null;
                nextFirst += 1;
            }
            size -= 1;
            return itemReturn;
        }
        return null;
    }

    @Override
    public T removeLast() {
        T itemReturn;

        if (size != 0) {
            if (size >= SIZEFIXED && (size - 1) < items.length / REDUCESIZEFACTOR) {
                resize(items.length / REDUCESIZEFACTOR);
            }
            if (nextLast == 0) {
                itemReturn  = items[items.length - 1];
                items[items.length - 1] = null;
                nextLast = items.length - 1;
            } else {
                itemReturn = items[nextLast - 1];
                items[nextLast - 1] = null;
                nextLast -= 1;
            }
            size -= 1;
            return itemReturn;
        }
        return null;

    }
    @Override
    /**to access "deque[index]" we need to consider if this element is in the "first part" or "last part"   */
    public T get(int index) {
        if (size == 0) {
            return null;
        }
        if (index < size && index >= 0) {
            if (nextFirst == items.length - 1) {
                return items[index];
            } else if ((nextFirst + index + 1) < items.length) {
                return items[nextFirst + index + 1];
            } else {
                return items[index - (items.length - nextFirst - 1)];
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Deque<?>) {
            Deque lstToCompare = (Deque) o;

            if (lstToCompare.size() != this.size()) {
                return false;
            } else {
                for (int i = 0; i < lstToCompare.size(); i++) {
                    if (!lstToCompare.get(i).equals(this.get(i))) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

    private class MyIterator<T> implements Iterator<T> {
        private int wizPos;

        public MyIterator() {
            wizPos = 0;
        }

        public boolean hasNext() {
            return wizPos < size;
        }

        public T next() {
            T returnItem;
            if (nextFirst == items.length - 1) {
                returnItem = (T) items[wizPos];
            } else if ((nextFirst + wizPos + 1) < items.length) {
                returnItem = (T) items[nextFirst + wizPos + 1];
            } else {
                returnItem = (T) items[wizPos - (items.length - nextFirst - 1)];
            }
            wizPos += 1;
            return returnItem;

        }
    }
    public Iterator<T> iterator() {
        return new MyIterator<>();
    }













}
