package com.upmc.algav.heap;

import com.upmc.algav.key.IKey128;

public interface MinHeap extends Heap<IKey128> {
    IKey128 deleteMin();
}
