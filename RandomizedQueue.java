import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items; // 8 byte * n + 24 byte
    private int n; // 4 byte
    // padding 4 byte

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[10];
    }

    public RandomizedQueue(int capacity) {
        items = (Item[]) new Object[capacity];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        checkForNull(item);

        if (n == items.length) {
            resize(2 * items.length);
        }
        items[n++] = item;
    }

    private void checkForNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    // remove and return a random item
    public Item dequeue() {
        checkIsEmpty();

        int randomNum = getRandomNum();
        Item item = items[randomNum];

        for (int i = randomNum; i < n - 1; i++) {
            items[i] = items[i + 1];
        }
        items[--n] = null;

        if (n > 0 && n == items.length / 4) {
            resize(items.length / 2);
        }

        return item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        checkIsEmpty();
        return items[getRandomNum()];
    }

    private int getRandomNum() {
        return StdRandom.uniform(n);
    }

    private void checkIsEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Item[] s;
        private int N;

        public ListIterator() {
            N = n;
            s = (Item[]) new Object[N];
            for (int i = 0; i < N; i++) {
                s[i] = items[i];
            }
            StdRandom.shuffle(s);
        }

        public boolean hasNext() {
            return N > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return s[--N];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<>();
        assert test.isEmpty();

        for (int i = 0; i < 5; i++) {
            test.enqueue(i);
        }
        for (Integer i : test) {
            StdOut.println(i);
        }
        assert test.isEmpty() == false;

        StdOut.println("deque: ");
        StdOut.println(test.dequeue());
        StdOut.println(test.dequeue());
        StdOut.println(test.dequeue());

        StdOut.println("First loop");
        for (Integer i : test) {
            StdOut.println(i);
        }

        StdOut.println("Second loop");
        for (Integer i : test) {
            StdOut.println(i);
        }
    }

}