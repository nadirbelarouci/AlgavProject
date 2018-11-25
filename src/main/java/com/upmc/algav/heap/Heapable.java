package com.upmc.algav.heap;

import java.util.Collection;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

interface Heapable<T, R> {
    static <T extends Comparable<T>, R> R min(Heapable<T, R> heap, R i, R j) {
        T first = heap.get(i);
        T second = heap.get(j);

        return first.compareTo(second) < 0 ? i : j;

    }

    static <T extends Comparable<T>, R> T deleteMin(Heapable<T, R> heap, Runnable swapAndRemoveLast) {
        Function<R, R> smallest = i -> min(heap, min(heap, i, heap.left(i)), heap.right(i));
        return delete(heap, swapAndRemoveLast, smallest);
    }

    static <T extends Comparable<T>, R> Function<R, R> min(Heapable<T, R> heap) {
        return i -> min(heap, min(heap, i, heap.left(i)), heap.right(i));
    }

    static <T extends Comparable<T>, R> T delete(Heapable<T, R> heap, Runnable swapAndRemoveLast, Function<R, R> best) {
        T min = heap.get(heap.root());
        swapAndRemoveLast.run();
        heapifyDown(heap, heap.root(), best);
        return min;
    }

    static <T extends Comparable<T>, R> void heapifyUp(Heapable<T, R> heap, R start,
                                                       Predicate<R> notRoot, BiPredicate<T, T> compare) {
        T current = heap.get(start);
        T parent = heap.get(heap.parent(start));
        if (notRoot.test(start) && compare.test(current, parent)) {
            heap.swap(start, heap.parent(start));
            heapifyUp(heap, heap.parent(start), notRoot, compare);
        }
    }

    static <T extends Comparable<T>, R> void heapifyDown(Heapable<T, R> heap, R start,
                                                         Function<R, R> best) {
        R bestVaue = best.apply(start);
        if (bestVaue != start) {
            heap.swap(start, bestVaue);
            heapifyDown(heap, bestVaue, best);
        }
    }

    R left(R r);

    R right(R r);

    R parent(R r);

    R root();

    R last();

    T get(R r);

    void swap(R first, R second);

    Collection<T> elements();


}
