package com.upmc.algav.heap;

import com.upmc.algav.key.IKey128;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.PriorityQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PriorityQueueTest extends MinHeapTest {
    private PriorityQueue<IKey128> heap;

    public PriorityQueueTest(Path path) throws IOException {
        super(path);
        heap = new PriorityQueue<>(keys);
    }

    @Override
    @Test
    public void deleteMin() {
        sortedKeys.forEach(key -> assertEquals(key, heap.poll()));
        assertTrue(heap.isEmpty());
    }

}
