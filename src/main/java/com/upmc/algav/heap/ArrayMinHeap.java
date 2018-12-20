package com.upmc.algav.heap;

import com.upmc.algav.key.IKey128;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class ArrayMinHeap implements MinHeap {

    private ArrayMinHeapImpl arrayMinHeap = new ArrayMinHeapImpl();

    public ArrayMinHeap(List<IKey128> values) {
        build(values);
    }

    @Override
    public boolean isEmpty() {
        return arrayMinHeap.empty();
    }

    @Override
    public IKey128 deleteMin() {
        return arrayMinHeap.deleteMin();
    }

    @Override
    public void insert(IKey128 key) {
        arrayMinHeap.add(key);
    }

    @Override
    public void build(List<IKey128> elements) {
        arrayMinHeap.values = new ArrayList<>(elements);
        for (int i = elements.size() / 2; i >= 0; i--) {
            arrayMinHeap.heapifyDown(i, arrayMinHeap.MIN());
        }
    }

    @Override
    public Collection<IKey128> elements() {
        return arrayMinHeap.values;
    }

    @Override
    public ArrayMinHeap union(Heap<IKey128> other) {
        List<IKey128> all = new ArrayList<>();
        all.addAll(arrayMinHeap.values);
        all.addAll(other.elements());
        return new ArrayMinHeap(all);
    }

    @Override
    public int size() {
        return arrayMinHeap.size();
    }

    ArrayMinHeapImpl getArrayMinHeap() {
        return arrayMinHeap;
    }

    @Override
    public String toString() {
        return arrayMinHeap.values.toString();
    }

    private static class ArrayMinHeapImpl implements BinaryHeap<Integer> {

        private List<IKey128> values = new ArrayList<>();

        private void add(IKey128 key) {
            values.add(key);
            heapifyUp(last(), i -> i > 0, IKey128::less);
        }

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
        public IKey128 get(Integer i) {
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
        public Collection<IKey128> elements() {
            return values;
        }


        @Override
        public void swapAndRemoveLast() {
            swap(root(), last());
            values.remove((int) last());
        }

        @Override
        public boolean empty() {
            return values.size() == 0;
        }

        @Override
        public int size() {
            return values.size();
        }

        @Override
        public void remove(Integer i) {
            values.remove((int) i);
        }
    }
}
