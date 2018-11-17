package com.upmc.algav.heap;

import com.upmc.algav.key.Key128;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ArrayMinHeap implements MinHeap<Key128> {

    private ArrayMinHeapImpl<Key128> arrayMinHeap;

    public ArrayMinHeap(Collection<Key128> values) {
        arrayMinHeap = new ArrayMinHeapImpl<>();
        build(values);
    }

    @Override
    public boolean empty() {
        return arrayMinHeap.values.size() == 0;
    }

    @Override
    public Key128 deleteMin() {
        if (empty())
            return null;
        if (arrayMinHeap.values.size() == 1) {
            Key128 key128 = arrayMinHeap.get(0);
            arrayMinHeap.values.remove(0);
            return key128;
        }
        return Heapable.deleteMin(arrayMinHeap,
                () -> {
                    arrayMinHeap.swap(arrayMinHeap.root(), arrayMinHeap.values.size() - 1);
                    arrayMinHeap.values.remove(arrayMinHeap.values.size() - 1);
                });
    }

    @Override
    public void insert(Key128 key) {
        arrayMinHeap.values.add(key);
        Heapable.heapifyUp(arrayMinHeap, arrayMinHeap.last(),
                i -> i > 0, Key128::less);
    }


    @Override
    public Collection<Key128> elements() {
        return arrayMinHeap.values;
    }

    @Override
    public Heap<Key128> union(Heap<Key128> other) {
        List<Key128> all = new ArrayList<>();
        all.addAll(arrayMinHeap.values);
        all.addAll(other.elements());
        return new ArrayMinHeap(all);
    }

    ArrayMinHeapImpl<Key128> getArrayMinHeap() {
        return arrayMinHeap;
    }

    @Override
    public String toString() {
        return arrayMinHeap.values.toString();
    }

    private static class ArrayMinHeapImpl<T extends Comparable<T>> implements Heapable<T, Integer> {
        private List<T> values = new ArrayList<>();


        @Override
        public Integer left(Integer i) {
            return i * 2 + 1;
        }

        public Integer right(Integer i) {
            return i * 2 + 2;
        }

        public Integer parent(Integer i) {
            return (i - 1) / 2;
        }

        @Override
        public T get(Integer i) {
            return i < values.size() ? values.get(i) : null;
        }

        @Override
        public void swap(Integer first, Integer second) {
            Collections.swap(values, first, second);
        }

        @Override
        public Integer root() {
            return 0;
        }

        @Override
        public Integer last() {
            return values.size() - 1;
        }

        @Override
        public Collection<T> elements() {
            return values;
        }
    }
}
