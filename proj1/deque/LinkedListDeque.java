package deque;
import java.util.Iterator;


public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private class SmartNode {
        private T item;
        private SmartNode next;
        private SmartNode prev;

        public SmartNode(T i, SmartNode p, SmartNode n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private SmartNode sentinel;
    private int size;

    /**
     * create an empty list
     */
    public LinkedListDeque() {
        sentinel = new SmartNode(null, null, null);
        size = 0;
    }

    /**
     * seems we don't need this constructor
     *    public LinkedListDeque(T x) {
     *         sentinel = new SmartNode(null, null, null);
     *         SmartNode newNode = new SmartNode(x, sentinel, sentinel);
     *         sentinel.prev = newNode;
     *         sentinel.next = newNode;
     *         size = 1;
     *     }
     */


    /**
     * add an item (a SmartNode) to the front of the list.By
     * 1.create a new smartNode firstly and assign its prev to sentinel
     * and next to sentinel.next (if sentinel.next is not null)
     * 2. (if sentinel.next is null) assign the next to sentinel itself.
     * 3. Most important thing is to connect all the pre and next.
     * Notice the pointer point to the whole SmartNode instead of the specific position of the Node.
     */

    @Override
    public void addFirst(T x) {
        SmartNode newNode;
        if (sentinel.next == null) {
            newNode = new SmartNode(x, sentinel, sentinel);
            sentinel.prev = newNode;
        } else {
            newNode = new SmartNode(x, sentinel, sentinel.next);
            sentinel.next.prev = newNode;
        }
        sentinel.next = newNode;
        size += 1;

    }
    @Override
    public void addLast(T x) {
        SmartNode newNode;
        if (size == 0) {
            newNode = new SmartNode(x, sentinel, sentinel);
            sentinel.next = newNode;
        } else {
            newNode = new SmartNode(x, sentinel.prev, sentinel);
            sentinel.prev.next = newNode;
        }

        sentinel.prev = newNode;
        size += 1;



    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        SmartNode ptr = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.print(ptr.item);
            System.out.print(" ");
            ptr = ptr.next;
        }
        System.out.println();

    }

    @Override
    public T removeFirst() {
        if (size != 0) {
            T itemRemoved = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            if (size == 1) {
                sentinel.prev = sentinel;
            }
            sentinel.next.prev = sentinel;
            size -= 1;
            return itemRemoved;
        }
        return null;

    }

    @Override
    public T removeLast() {
        if (size != 0) {
            T itemRemoved = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return itemRemoved;
        }
        return null;

    }

    @Override
    public T get(int index) {
        if (size == 0) {
            return null;
        }
        SmartNode ptr = sentinel.next;
        while (index != 0) {
            ptr = ptr.next;
            index -= 1;
        }
        return ptr.item;
    }

    public T getRecursive(int index) {
        if (sentinel.next == null) {
            return null;
        }
        return helperGetRecuresive(index, sentinel.next);
    }
    private T helperGetRecuresive(int i, SmartNode node) {
        if (i == 0) {
            return node.item;
        }
        return helperGetRecuresive(i - 1, node.next);

    }


    @Override
    public boolean equals(Object o) {

        if (o instanceof Deque<?>) {
            Deque lstToCompare = (Deque) o;
            if (lstToCompare.size() != this.size()) {
                return false;
            } else {
                for (int i = 0; i < size; i++) {
                    if (!this.get(i).equals(lstToCompare.get(i))) {
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
            SmartNode ptr = sentinel.next;
            int i = wizPos;
            while (i > 0) {
                ptr = ptr.next;
                i = i - 1;
            }
            returnItem = (T) ptr.item;
            wizPos += 1;
            return returnItem;

        }
    }

    public Iterator<T> iterator() {
        return new MyIterator<>();
    }



















}






