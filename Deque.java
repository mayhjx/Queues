import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int n;

    private class Node {
        public Node(Item item, Node prev, Node next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }

        Node prev;
        Item item;
        Node next;
    }

    // construct an empty deque
    public Deque() {
        first = last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null && last == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        checkForNull(item);

        Node oldFirst = first;
        first = new Node(item, null, oldFirst);
        if (last == null) {
            first.next = last;
        } else {
            oldFirst.prev = first;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        checkForNull(item);

        Node oldLast = last;
        last = new Node(item, oldLast, null);
        if (oldLast != null) {
            oldLast.next = last;
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
        first.prev = null;
        n--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        checkIsEmpty();

        Item item = last.item;
        last = last.prev;
        last.next = null;
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
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addLast(0);
        assert deque.isEmpty() == false;
        for (Integer i : deque) {
            StdOut.println(i);
        }
    }

}