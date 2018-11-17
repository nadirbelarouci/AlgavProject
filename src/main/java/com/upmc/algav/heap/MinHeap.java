package com.upmc.algav.heap;

public interface MinHeap<T extends Comparable<T>> extends Heap<T> {
    T deleteMin();
}
