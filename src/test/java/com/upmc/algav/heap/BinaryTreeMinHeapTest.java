package com.upmc.algav.heap;

import com.upmc.algav.key.Key128;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.Assert.assertTrue;

public class BinaryTreeMinHeapTest extends MinHeapTest {
    public BinaryTreeMinHeapTest(Path path) throws IOException {
        super(path);
        heap = new BinaryTreeMinHeap(keys);
    }

    private static void checkMinHeapProperty(Heapable<Key128, IBinaryTreeHeapNode<Key128>> minHeap, IBinaryTreeHeapNode<Key128> root) {
        if (root != null) {
            assertTrue(minHeap.get(root).less(minHeap.get(minHeap.left(root))));
            assertTrue(minHeap.get(root).less(minHeap.get(minHeap.right(root))));
            checkMinHeapProperty(minHeap, (IBinaryTreeHeapNode<Key128>) root.left());
            checkMinHeapProperty(minHeap, (IBinaryTreeHeapNode<Key128>) root.right());
        }

    }

    @Test
    public void build() {
        Heapable<Key128, IBinaryTreeHeapNode<Key128>> tree = ((BinaryTreeMinHeap) heap).getTree();
        checkMinHeapProperty(tree, tree.root());
    }

//    @Test
//    public void union() {
//        List<Key128> otherList = Stream.of(32, 10, 40, 25, 60, 1, 4)
//                .map(String::valueOf)
//                .map(Key128::new)
//                .collect(Collectors.toList());
//        BinaryTreeMinHeap union = (BinaryTreeMinHeap) minHeap.union(new BinaryTreeMinHeap(otherList));
//        checkMinHeapProperty(union.getTree(), union.getTree().root());
//    }
}