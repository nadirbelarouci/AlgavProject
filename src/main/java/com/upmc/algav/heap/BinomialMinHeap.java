/******************************************************************************
 *  Compilation: javac BinomialMinHeap.java
 *  Execution:
 *
 *  A binomial heap.
 *
 ******************************************************************************/

package com.upmc.algav.heap;

import com.upmc.algav.interfaces.Heap;
import com.upmc.algav.interfaces.IKey128;
import com.upmc.algav.interfaces.MinHeap;

import java.util.*;

/**
 * The BinomialMinHeap class represents a priority queue of generic keys.
 * It supports the usual insert and delete-the-minimum operations,
 * along with the merging of two heaps together.
 * It also supports methods for peeking at the minimum key,
 * testing if the priority queue is isEmpty, and iterating through
 * the keys.
 * It is possible to build the priority queue using a Comparator.
 * If not, the natural order relation between the keys will be used.
 * <p>
 * This implementation uses a binomial heap.
 * The insert, delete-the-minimum, union, MIN-key
 * and size operations take logarithmic time.
 * The is-isEmpty and constructor operations take constant time.
 *
 * @author Tristan Claverie
 */
public class BinomialMinHeap implements MinHeap, Iterable<IKey128> {
    private final Comparator<IKey128> comp;    //Comparator over the keys
    private Node head;                    //head of the list of roots

    /**
     * Initializes an isEmpty priority queue
     * Worst case is O(1)
     */
    public BinomialMinHeap() {
        comp = new MyComparator();
    }

    /**
     * Initializes an isEmpty priority queue using the given Comparator
     * Worst case is O(1)
     *
     * @param C a comparator over the keys
     */
    public BinomialMinHeap(Comparator<IKey128> C) {
        comp = C;
    }

    /**
     * Initializes a priority queue with given keys
     * Worst case is O(n*log(n))
     *
     * @param a an array of keys
     */
    public BinomialMinHeap(Collection<IKey128> a) {
        comp = new MyComparator();
        for (IKey128 k : a) insert(k);
    }

    /**
     * Initializes a priority queue with given keys using the given Comparator
     * Worst case is O(n*log(n))
     *
     * @param C a comparator over the keys
     * @param a an array of keys
     */
    public BinomialMinHeap(Comparator<IKey128> C, IKey128[] a) {
        comp = C;
        for (IKey128 k : a) insert(k);
    }

    /**
     * Whether the priority queue is isEmpty
     * Worst case is O(1)
     *
     * @return true if the priority queue is isEmpty, false if not
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Number of elements currently on the priority queue
     * Worst case is O(log(n))
     *
     * @return the number of elements on the priority queue
     * @throws java.lang.ArithmeticException if there are more than 2^63-1 elements in the queue
     */
    public int size() {
        int result = 0;
        int tmp;
        for (Node node = head; node != null; node = node.sibling) {
            if (node.order > 30) {
                throw new ArithmeticException("The number of elements cannot be evaluated, but the priority queue is still valid.");
            }
            tmp = 1 << node.order;
            result |= tmp;
        }
        return result;
    }

    @Override
    public Collection<IKey128> elements() {
        Iterator<IKey128> it = iterator();
        List<IKey128> elements = new ArrayList<>();
        while (it.hasNext())
            elements.add(it.next());

        return elements;
    }

    /**
     * Puts a IKey128 in the heap
     * Worst case is O(log(n))
     *
     * @param key a IKey128
     */
    public void insert(IKey128 key) {
        Node x = new Node();
        x.key = key;
        x.order = 0;
        BinomialMinHeap H = new BinomialMinHeap(); //The Comparator oh the H heap is not used
        H.head = x;
        this.head = this.union(H).head;
    }

    /**
     * Get the minimum key currently in the queue
     * Worst case is O(log(n))
     *
     * @return the minimum key currently in the priority queue
     * @throws java.util.NoSuchElementException if the priority queue is isEmpty
     */
    public IKey128 minKey() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue is isEmpty");
        Node min = head;
        Node current = head;
        while (current.sibling != null) {
            min = (greater(min.key, current.sibling.key)) ? current : min;
            current = current.sibling;
        }
        return min.key;
    }

    /**
     * Deletes the minimum key
     * Worst case is O(log(n))
     *
     * @return the minimum key
     * @throws java.util.NoSuchElementException if the priority queue is isEmpty
     */
    public IKey128 deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue is isEmpty");
        Node min = eraseMin();
        Node x = (min.child == null) ? min : min.child;
        if (min.child != null) {
            min.child = null;
            Node prevx = null, nextx = x.sibling;
            while (nextx != null) {
                x.sibling = prevx;
                prevx = x;
                x = nextx;
                nextx = nextx.sibling;
            }
            x.sibling = prevx;
            BinomialMinHeap H = new BinomialMinHeap();
            H.head = x;
            head = union(H).head;
        }
        return min.key;
    }

    /**
     * Merges two Binomial heaps together
     * This operation is destructive
     * Worst case is O(log(n))
     *
     * @param other a Binomial Heap to be merged with the current heap
     * @return the union of two heaps
     * @throws java.lang.IllegalArgumentException if the heap in parameter is null
     */
    public BinomialMinHeap union(Heap<IKey128> other) {
        if ((!other.getClass().equals(this.getClass())))
            other = new BinomialMinHeap(other.elements());
        BinomialMinHeap heap = (BinomialMinHeap) other;
        if (heap == null) throw new IllegalArgumentException("Cannot merge a Binomial Heap with null");
        this.head = merge(new Node(), this.head, heap.head).sibling;
        Node x = this.head;
        Node prevx = null, nextx = x.sibling;
        while (nextx != null) {
            if (x.order < nextx.order ||
                    (nextx.sibling != null && nextx.sibling.order == x.order)) {
                prevx = x;
                x = nextx;
            } else if (greater(nextx.key, x.key)) {
                x.sibling = nextx.sibling;
                link(nextx, x);
            } else {
                if (prevx == null) {
                    this.head = nextx;
                } else {
                    prevx.sibling = nextx;
                }
                link(x, nextx);
                x = nextx;
            }
            nextx = x.sibling;
        }
        return this;
    }

    /*************************************************
     * General helper functions
     ************************************************/

    //Compares two keys
    private boolean greater(IKey128 n, IKey128 m) {
        if (n == null) return false;
        if (m == null) return true;
        return comp.compare(n, m) > 0;
    }

    //Assuming root1 holds a greater key than root2, root2 becomes the new root
    private void link(Node root1, Node root2) {
        root1.sibling = root2.child;
        root2.child = root1;
        root2.order++;
    }

    //Deletes and return the node containing the minimum key
    private Node eraseMin() {
        Node min = head;
        Node previous = null;
        Node current = head;
        while (current.sibling != null) {
            if (greater(min.key, current.sibling.key)) {
                previous = current;
                min = current.sibling;
            }
            current = current.sibling;
        }
        if (previous != null)
            previous.sibling = min.sibling;

        if (min == head) head = min.sibling;
        return min;
    }

    /**************************************************
     * Functions for inserting a key in the heap
     *************************************************/

    //Merges two root lists into one, there can be up to 2 Binomial Trees of same order
    private Node merge(Node h, Node x, Node y) {
        if (x == null && y == null) return h;
        else if (x == null) h.sibling = merge(y, null, y.sibling);
        else if (y == null) h.sibling = merge(x, x.sibling, null);
        else if (x.order < y.order) h.sibling = merge(x, x.sibling, y);
        else h.sibling = merge(y, x, y.sibling);
        return h;
    }

    /**
     * Gets an Iterator over the keys in the priority queue in ascending order
     * The Iterator does not implement the remove() method
     * iterator() : Worst case is O(n)
     * next() : 	Worst case is O(log(n))
     * hasNext() : 	Worst case is O(1)
     *
     * @return an Iterator over the keys in the priority queue in ascending order
     */
    public Iterator<IKey128> iterator() {
        return new MyIterator();
    }

    /***************************
     * Comparator
     **************************/

    //default Comparator
    private static class MyComparator<IKey128 extends Comparable<IKey128>> implements Comparator<IKey128> {
        @Override
        public int compare(IKey128 key1, IKey128 key2) {
            return key1.compareTo(key2);
        }
    }

    /******************************************************************
     * Iterator
     *****************************************************************/

    //Represents a Node of a Binomial Tree
    private class Node {
        IKey128 key;                        //IKey128 contained by the Node
        int order;                        //The order of the Binomial Tree rooted by this Node
        Node child, sibling;            //child and sibling of this Node
    }

    private class MyIterator implements Iterator<IKey128> {
        BinomialMinHeap data;

        //Constructor clones recursively the elements in the queue
        //It takes linear time
        public MyIterator() {
            data = new BinomialMinHeap(comp);
            data.head = clone(head, null);
        }

        private Node clone(Node x, Node parent) {
            if (x == null) return null;
            Node node = new Node();
            node.key = x.key;
            node.sibling = clone(x.sibling, parent);
            node.child = clone(x.child, node);
            return node;
        }

        public boolean hasNext() {
            return !data.isEmpty();
        }

        public IKey128 next() {
            if (!hasNext()) throw new NoSuchElementException();
            return data.deleteMin();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}