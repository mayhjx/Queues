import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int n;

    private class Node {
        public Node() {
        }

        Node prev;
        Item item;
        Node next;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        checkForNull(item);

        if (isEmpty()) {
            first = new Node();
            first.item = item;
            last = first;
        } else {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            oldfirst.prev = first;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        checkForNull(item);

        if (isEmpty()) {
            last = new Node();
            last.item = item;
            first = last;
        } else {
            Node oldlast = last;
            last = new Node();
            last.item = item;
            last.prev = oldlast;
            oldlast.next = last;
        }
        n++;
    }

    private void checkForNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        checkIsEmpty();

        Item item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
        } else {
            first.prev = null;
        }
        n--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        checkIsEmpty();

        Item item = last.item;
        last = last.prev;
        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }
        n--;
        return item;
    }

    private void checkIsEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (isEmpty()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        assert deque.isEmpty();

        System.out.println("Test addFirst");
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);

        System.out.println("Test addLast");
        deque.addLast(0);
        deque.addLast(-1);
        deque.addLast(-2);

        for (Integer i : deque) {
            System.out.println(i);
        }

        System.out.println("size: " + deque.size());

        System.out.println("Test removeFirst: ");
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        // System.out.println(deque.removeFirst());
        // System.out.println(deque.removeFirst());
        // System.out.println(deque.removeFirst());

        System.out.println("Test removeLast: ");
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        // System.out.println(deque.removeLast());
        // System.out.println(deque.removeLast());
        // System.out.println(deque.removeLast());

        System.out.println("deque should be empty now.");
        assert deque.isEmpty();
        for (Integer i : deque) {
            System.out.println(i);
        }
    }

}