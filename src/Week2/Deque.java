package Week2;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Zoro on 17-4-27.
 */

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;

    private int N = 0;
    public Deque() {}                // construct an empty deque
    public boolean isEmpty() {
        return N == 0;
    }                 // is the deque empty?
    public int size() {
        return N;
    }                       // return the number of items on the deque
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        if (isEmpty()) {
            last = first;
        } else {
            oldFirst.previous = first;
            first.next = oldFirst;
        }
        N++;
    }         // add the item to the front
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
            last.previous = oldLast;
        }
        N++;
    }          // add the item to the end
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        if (N != 1) {
            first.previous = null;
        }
        N--;
        return item;

    }                // remove and return the item from the front
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        last = last.previous;
        if (N != 1) {
            last.next = null;
        }
        N--;
        return item;
    }                // remove and return the item from the end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }        // return an iterator over items in order from front to end
    public static void main(String[] args) {
        Deque<String> stringDeque = new Deque<>();
        stringDeque.addFirst("Hello");
        stringDeque.addLast("World");
        for (String s : stringDeque) {
            System.out.println(s);
        }
        System.out.println(stringDeque.removeFirst());
        System.out.println(stringDeque.removeLast());
    }  // unit testing (optional)
    private class Node {
        Item item;
        Node previous;
        Node next;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}