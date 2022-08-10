/******************************************************************************
 *  Compilation:  javac RandomizedQueue.java
 *  Execution:    java RandomizedQueue
 *  Dependencies: StdIn.java StdOut.java
 *
 *  A randomized queue, implemented using a resize array.
 *  Each deque element is of type Item.
 *
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@code RandomizedQueue} class represents a first-in-first-out (FIFO)
 * queue of generic items. It supports the usual <em>enqueue</em> and
 * <em>dequeue</em> operations, along with methods for testing if the queue
 * is empty, and iterating through the items in FIFO order.
 * <p>
 * This implementation uses a resizing array.
 * <p>
 *
 * @param <Item> the generic type of item in this deque
 * @author Xue Zhang
 */

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int INIT_CAPACITY = 8;
    private Item[] a;
    private int lastIndex;

    public RandomizedQueue() {
        lastIndex = -1;
        a = (Item[]) new Object[INIT_CAPACITY];
    }

    /**
     * Is the randomized queue empty?
     *
     * @return true if this randomized queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Return the number of items on the randomized queue.
     *
     * @return the number of items on the randomized queue
     */
    public int size() {
        return lastIndex + 1;
    }


    /**
     * Resize the current array
     *
     * @param capacity the length of the current array
     */
    private void resize(int capacity) {
        Item[] newArray = (Item[]) new Object[capacity];
        int i = 0, j = 0;
        while (i <= lastIndex) {
            newArray[j++] = a[i++];
        }
        a = newArray;
        lastIndex = j - 1;
    }

    /**
     * Adds the item to this randomized queue.
     *
     * @param item the item to add
     * @throws IllegalArgumentException if the item is null
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Element e can't be null");
        }
        if (lastIndex + 1 == a.length) {
            resize(a.length * 2);
        }
        a[++lastIndex] = item;
    }

    /**
     * Removes and returns a random item on this randomized queue
     *
     * @return a random item on this queue
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("The randomized queue is underflow.");
        }
        int index = StdRandom.uniform(lastIndex + 1);
        Item removeItem = a[index];
        a[index] = a[lastIndex];
        a[lastIndex--] = null;
        if (size() > 0 && size() == a.length / 4) {
            resize(a.length / 2);
        }
        return removeItem;
    }

    /**
     * Returns a random item (but do not remove it).
     *
     * @return a random item on the randomized queue
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("the randomized queue is underflow.");
        }
        Item chooseItem = null;
        while (chooseItem == null) {
            chooseItem = a[StdRandom.uniform(lastIndex + 1)];
        }
        return chooseItem;
    }


    /**
     * Returns an independent iterator over items in random order.
     *
     * @return an independent iterator over items in random order
     */

    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {

        private Item[] copiedArray;
        private int copiedLastIndex;

        RandomizedIterator() {
            Item[] array = (Item[]) new Object[lastIndex + 1];
            for (int i = 0; i <= lastIndex; i++) {
                array[i] = a[i];
            }
            copiedArray = array;
            copiedLastIndex = lastIndex;
        }

        @Override
        public boolean hasNext() {
            return copiedLastIndex >= 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more item.");
            }
            int i = StdRandom.uniform(copiedLastIndex + 1);
            Item item = copiedArray[i];
            copiedArray[i] = copiedArray[copiedLastIndex];
            copiedArray[copiedLastIndex--] = null;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove unsupported. ");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Item item : this) {
            sb.append(item);
            sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {

        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        StdOut.println(randomizedQueue.size());
        StdOut.println(randomizedQueue.isEmpty());
        randomizedQueue.enqueue(1);
        randomizedQueue.enqueue(2);
        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(4);
        randomizedQueue.enqueue(5);
        StdOut.println(randomizedQueue);
        StdOut.println("The total size = " + randomizedQueue.size());
        StdOut.println(randomizedQueue.dequeue());
        StdOut.println(randomizedQueue.dequeue());
        StdOut.println(randomizedQueue.dequeue());
        StdOut.println(randomizedQueue);
        StdOut.println("The total size = " + randomizedQueue.size());
    }
}
