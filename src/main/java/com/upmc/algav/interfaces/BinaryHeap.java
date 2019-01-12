package com.upmc.algav.interfaces;

import java.util.Collection;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public interface BinaryHeap<Node> {
    default void heapifyDown(Node start, Function<Node, Node> best) {
        Node bestVaue = best.apply(start);
        if (bestVaue != start) {
            swap(start, bestVaue);
            heapifyDown(bestVaue, best);
        }
    }

    default void heapifyUp(Node start, Predicate<Node> notRoot, BiPredicate<IKey128, IKey128> best) {
        IKey128 current = get(start);
        IKey128 parent = get(parent(start));
        if (notRoot.test(start) && best.test(current, parent)) {
            swap(start, parent(start));
            heapifyUp(parent(start), notRoot, best);
        }
    }

    default IKey128 deleteMin() {
        if (empty())
            return null;

        if (size() == 1) {
            IKey128 key = get(root());
            remove(root());
            return key;
        }
        return delete(MIN());
    }

    default IKey128 delete(Function<Node, Node> best) {
        IKey128 min = get(root());
        swapAndRemoveLast();
        heapifyDown(root(), best);
        return min;
    }

    default Node min(Node i, Node j) {
        return get(i).less(get(j)) ? i : j;
    }

    default Function<Node, Node> MIN() {
        return i -> min(min(i, left(i)), right(i));
    }

    Node left(Node node);

    Node right(Node node);

    Node parent(Node node);

    Node root();

    Node last();

    boolean empty();

    int size();

    IKey128 get(Node node);

    void swap(Node first, Node second);

    void swapAndRemoveLast();

    void remove(Node i);

    Collection<IKey128> elements();


}
