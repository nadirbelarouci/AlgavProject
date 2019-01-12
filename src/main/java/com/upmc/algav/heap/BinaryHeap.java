package com.upmc.algav.heap;

import com.upmc.algav.key.IKey128;

import java.util.Collection;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

interface BinaryHeap<Index> {
    default void heapifyDown(Index start, Function<Index, Index> best) {
        Index bestVaue = best.apply(start);
        if (bestVaue != start) {
            swap(start, bestVaue);
            heapifyDown(bestVaue, best);
        }
    }

    default void heapifyUp(Index start, Predicate<Index> notRoot, BiPredicate<IKey128, IKey128> best) {
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

    default IKey128 delete(Function<Index, Index> best) {
        IKey128 min = get(root());
        swapAndRemoveLast();
        heapifyDown(root(), best);
        return min;
    }

    default Index min(Index i, Index j) {
        return get(i).less(get(j)) ? i : j;
    }

    default Function<Index, Index> MIN() {
        return i -> min(min(i, left(i)), right(i));
    }

    Index left(Index index);

    Index right(Index index);

    Index parent(Index index);

    Index root();

    Index last();

    boolean empty();

    int size();

    IKey128 get(Index index);

    void swap(Index first, Index second);

    void swapAndRemoveLast();

    void remove(Index i);

    Collection<IKey128> elements();


}
