package com.upmc.algav.heap;

import com.upmc.algav.key.Key128;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class BinaryTreeMinHeapTest {
    private MinHeap<Key128, Node<Key128>> minHeap;

    private static void checkMinHeapProperty(MinHeap<Key128, Node<Key128>> minHeap, Node<Key128> root) {
        if (root != null) {
            assertTrue(minHeap.get(root).less(minHeap.get(minHeap.left(root))));
            assertTrue(minHeap.get(root).less(minHeap.get(minHeap.right(root))));
            checkMinHeapProperty(minHeap, root.left);
            checkMinHeapProperty(minHeap, root.right);
        }

    }

    @Before
    public void setUp() {
        minHeap = new BinaryTreeMinHeap(MinHeapTest.list);
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
        checkMinHeapProperty(minHeap, minHeap.root());
    }

    @Test
    public void union() {
        List<Key128> otherList = Stream.of(32, 10, 40, 25, 60, 1, 4)
                .map(String::valueOf)
                .map(Key128::new)
                .collect(Collectors.toList());
        BinaryTreeMinHeap union = (BinaryTreeMinHeap) minHeap.union(new BinaryTreeMinHeap(otherList));
        checkMinHeapProperty(minHeap, minHeap.root());
    }
}