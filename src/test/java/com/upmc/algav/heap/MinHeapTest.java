package com.upmc.algav.heap;

import com.upmc.algav.key.Key128;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayMinHeapTest.class,
        BinaryTreeMinHeapTest.class
})
public class MinHeapTest {
    static List<Key128> list = Stream.of(50, 100, 40, 30, 10, 15, 40)
            .map(String::valueOf)
            .map(Key128::new)
            .collect(Collectors.toList());

    public static void deleteMinTest(MinHeap<Key128, ?> minHeap) {


        assertEquals(new Key128("10"), minHeap.deleteMin());
        assertEquals(new Key128("15"), minHeap.deleteMin());
        assertEquals(new Key128("30"), minHeap.deleteMin());
        assertEquals(new Key128("40"), minHeap.deleteMin());
        assertEquals(new Key128("40"), minHeap.deleteMin());
        assertEquals(new Key128("50"), minHeap.deleteMin());
        assertEquals(new Key128("100"), minHeap.deleteMin());
        assertTrue(minHeap.empty());
        assertNull(minHeap.deleteMin());
    }


}
