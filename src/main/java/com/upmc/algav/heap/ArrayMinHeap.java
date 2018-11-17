package com.upmc.algav.heap;

import com.upmc.algav.key.Key128;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class ArrayMinHeap implements MinHeap<Key128, Integer> {
    private List<Key128> values = new ArrayList<>();

    public ArrayMinHeap(Collection<Key128> values) {
        build(values);
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
    public Key128 get(Integer i) {
        return i < values.size() ? values.get(i) : null;
    }

    @Override
    public void swap(Integer first, Integer second) {
        Collections.swap(values, first, second);
    }

    @Override
    public boolean empty() {
        return values.size() == 0;
    }

    @Override
    public Key128 deleteMin() {
        if (empty())
            return null;
        if (values.size() == 1) {
            Key128 key128 = get(0);
            values.remove(0);
            return key128;
        }
        return MinHeap.deleteMin(this,
                () -> {
                    swap(root(), values.size() - 1);
                    values.remove(values.size() - 1);
                });
    }

    @Override
    public void insert(Key128 key) {
        values.add(key);
        MinHeap.minHeapifyUp(this, last(),
                i -> i > 0, Key128::less);
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
    public Collection<Key128> elements() {
        return values;
    }

    @Override
    public Heap<Key128, Integer> union(Heap<Key128, Integer> other) {
        List<Key128> all = new ArrayList<>();
        all.addAll(values);
        all.addAll(other.elements());
        return new ArrayMinHeap(all);
    }

    @Override
    public String toString() {
        return values.toString();
    }
}
