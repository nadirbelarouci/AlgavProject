package com.upmc.algav.heap;

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public interface MinHeap<T extends Comparable<T>, R> extends Heap<T, R> {


    static <T extends Comparable<T>, R> R min(MinHeap<T, R> minHeap, R i, R j) {
        T first = minHeap.get(i);
        T second = minHeap.get(j);
        return first.compareTo(second) < 0 ? i : j;

    }

    static <T extends Comparable<T>, R> T deleteMin(MinHeap<T, R> minHeap, Runnable swapAndRemoveLast) {
        T min = minHeap.get(minHeap.root());
        swapAndRemoveLast.run();
        Function<R, R> smallest = i -> min(minHeap, min(minHeap, i, minHeap.left(i)), minHeap.right(i));
        minHeapifyDown(minHeap, minHeap.root(), smallest);
        return min;
    }

    static <T extends Comparable<T>, R> void minHeapifyUp(MinHeap<T, R> minHeap, R start,
                                                          Predicate<R> notRoot, BiPredicate<T, T> compare) {
        T current = minHeap.get(start);
        T parent = minHeap.get(minHeap.parent(start));
        if (notRoot.test(start) && compare.test(current, parent)) {
            minHeap.swap(start, minHeap.parent(start));
            minHeapifyUp(minHeap, minHeap.parent(start), notRoot, compare);
        }
    }

    static <T extends Comparable<T>, R> void minHeapifyDown(MinHeap<T, R> minHeap, R start,
                                                            Function<R, R> best) {
        R bestVaue = best.apply(start);
        if (bestVaue != start) {
            minHeap.swap(start, bestVaue);
            minHeapifyDown(minHeap, bestVaue, best);
        }
    }

    T deleteMin();


}
