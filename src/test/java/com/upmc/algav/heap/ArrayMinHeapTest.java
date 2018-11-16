package com.upmc.algav.heap;

import com.upmc.algav.key.Key128;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class ArrayMinHeapTest {

    private MinHeap<Key128, Integer> minHeap;

    private static void checkMinHeapProperty(MinHeap<Key128, Integer> minHeap) {
        for (int i = 0; i < minHeap.elements().size(); i++) {
            assertTrue(minHeap.get(i).less(minHeap.get(minHeap.left(i))));
            assertTrue(minHeap.get(i).less(minHeap.get(minHeap.right(i))));
        }

    }

    @Before
    public void setUp() {

        minHeap = new ArrayMinHeap(MinHeapTest.list);
    }

    @After
    public void tearDown() {

        minHeap = null;
    }

    @Test
    public void deleteMin() {
        MinHeapTest.deleteMinTest(minHeap);
    }

    @Test
    public void build() {
        checkMinHeapProperty(minHeap);
    }

    @Test
    public void union() {
        List<Key128> otherList = Stream.of(32, 10, 40, 25, 60, 1, 4)
                .map(String::valueOf)
                .map(Key128::new)
                .collect(Collectors.toList());
        ArrayMinHeap union = (ArrayMinHeap) minHeap.union(new ArrayMinHeap(otherList));

        checkMinHeapProperty(minHeap);


    }
}