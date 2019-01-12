package com.upmc.algav.heap;

import com.upmc.algav.interfaces.IKey128;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.PriorityQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BinomialMinHeapTest extends MinHeapTest {
    private BinomialMinHeap heap;

    public BinomialMinHeapTest(Path path) throws IOException {
        super(path);
        heap = new BinomialMinHeap(keys);
    }

    @Override
    @Test
    public void deleteMin() {
        sortedKeys.forEach(key -> assertEquals(key, heap.deleteMin()));
        assertTrue(heap.isEmpty());
    }

}
