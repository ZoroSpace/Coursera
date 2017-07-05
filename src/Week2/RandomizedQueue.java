package Week2;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Zoro on 17-4-28.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int N = 0;
    private Item[] a = (Item[]) new Object[1];

    public RandomizedQueue() {}                // construct an empty randomized queue
    public boolean isEmpty() {
        return N == 0;
    }                // is the queue empty?
    public int size() {
        return N;
    }                       // return the number of items on the queue

    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (N == a.length) resize(2*a.length);
        a[N++] = item;

    }          // add the item
    public Item dequeue() {
        if (N == 0) {
            throw new NoSuchElementException();
        }
        int i = StdRandom.uniform(N);
        Item tempItem = a[i];
        a[i] = a[N-1];
        a[N-1] = null;
        if (N>0 && N == a.length/4) resize(a.length/2);
        N--;
        return tempItem;
    }                   // remove and return a random item
    public Item sample() {
        if (N == 0) {
            throw new NoSuchElementException();
        }
        Item item = a[StdRandom.uniform(N)];
        return item;
    }                    // return (but do not remove) a random item
    public Iterator<Item> iterator() {
        return new RQIterator();
    }        // return an independent iterator over items in random order
    public static void main(String[] args) {

    }  // unit testing (optional)

    private class RQIterator implements Iterator<Item> {
        private int i = N;

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int j = StdRandom.uniform(i);
            Item tempItem = a[j];
            a[j] = a[i-1];
            a[i-1] = null;
            i--;
            return tempItem;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
