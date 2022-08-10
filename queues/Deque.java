/******************************************************************************
 *  Compilation:  javac Deque.java
 *  Execution:    java Deque
 *  Dependencies: StdIn.java StdOut.java
 *
 *  A generic deque, implemented using a singly double linked list.
 *  Each deque element is of type Item.
 *
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@code Deque} class represents both a last-in-first-out (LIFO) stack of
 * generic items and a first-in-first-out (FIFO) queue of generic items.
 * It supports the usual <em>addLast</em>, <em>addFirst</em>,
 * <em>removeFirst</em>, <em>removeLast</em>operations,
 * along with methods for testing if the deque is empty,
 * and iterating through the items in either LIFO or FIFO order.
 * <p>
 * This implementation uses a singly linked list with a static nested class for
 * linked-list nodes.
 * <p>
 *
 * @param <Item> the generic type of item in this deque
 * @author Xue Zhang
 */
public class Deque<Item> implements Iterable<Item> {

    private class Node {
        private final Item item;
        private Node pre;
        private Node next;

        public Node(Item item) {
            this.item = item;
        }
    }

    private int n;
    private final Node head;
    private final Node tail;

    /**
     * Initialize an empty deque.
     */
    public Deque() {
        n = 0;
        head = new Node(null);
        tail = new Node(null);
        head.next = tail;
        tail.pre = head;
    }

    /**
     * Is the deque empty?
     *
     * @return true is this deque is empty; false otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Returns the number of items on the deque.
     *
     * @return the number of items on the deque
     */
    public int size() {
        return n;
    }

    /**
     * Adds the item to the front of this deque.
     *
     * @param item the item to add
     * @throws IllegalArgumentException if the item is null
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("the item to add can't be null");
        }
        Node node = new Node(item);
        node.next = head.next;
        node.pre = head;
        head.next.pre = node;
        head.next = node;
        n++;
    }

    /**
     * Adds the item to the back of this deque.
     *
     * @param item the item to add
     * @throws IllegalArgumentException if the item is null
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("the item to add can't be null");
        }
        Node pre = tail.pre;
        Node node = new Node(item);
        node.pre = pre;
        node.next = tail;
        tail.pre = node;
        pre.next = node;
        n++;
    }

    /**
     * Removes and returns the item from the front of this deque.
     *
     * @return the item on this queue that was located in the front
     * @throws NoSuchElementException when the deque is empty
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("the deque is underflow");
        }
        Node next = head.next;
        Item item = next.item;
        head.next = next.next;
        next.next.pre = head;
        n--;
        return item;
    }

    /**
     * Removes and returns the item from the back of this deque.
     *
     * @return the item on this queue that was located in the back
     * @throws NoSuchElementException when the deque is empty
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("the deque is underflow");
        }
        Node pre = tail.pre;
        tail.pre = pre.pre;
        pre.pre.next = tail;
        n--;
        return pre.item;
    }

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

    /**
     * Return an iterator over items in order from front to back.
     *
     * @return an iterator over items in order from front to back
     */
    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }

    private class LinkedIterator implements Iterator<Item> {

        private Node cur = head;

        public boolean hasNext() {
            return cur.next != tail;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            cur = cur.next;
            return cur.item;
        }
    }

    public static void main(String[] args) {
        StdOut.println("Test begin");
        Deque<Integer> dq = new Deque<>();
        StdOut.println(dq.isEmpty());
        dq.addFirst(1);
        StdOut.println(dq);
        dq.addFirst(2);
        dq.addFirst(3);
        StdOut.println(dq);
        StdOut.println("The size of current deque is " + dq.size());
        dq.addLast(4);
        dq.addLast(5);
        StdOut.println(dq);
        StdOut.println("The size of current deque is " + dq.size());
        StdOut.println(dq.removeFirst());
        StdOut.println(dq.removeFirst());
        StdOut.println(dq);
        StdOut.println("The size of current deque is " + dq.size());
        StdOut.println(dq.removeLast());
        StdOut.println(dq.removeLast());
        StdOut.println(dq);
        StdOut.println("The size of current deque is " + dq.size());
    }


}
