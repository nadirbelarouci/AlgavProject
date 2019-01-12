package com.upmc.algav.heap;

import com.upmc.algav.interfaces.BinaryHeap;
import com.upmc.algav.interfaces.IBinaryTreeHeapNode;
import com.upmc.algav.interfaces.IKey128;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BinaryTreeMinHeapTest extends MinHeapTest {
    public BinaryTreeMinHeapTest(Path path) throws IOException {
        super(path);
        heap = new BinaryTreeMinHeap(keys);
    }

    private static void checkMinHeapProperty(BinaryHeap<IBinaryTreeHeapNode<IKey128>> minHeap, IBinaryTreeHeapNode<IKey128> root) {
        if (root != null) {
            assertTrue(minHeap.get(root).less(minHeap.get(minHeap.left(root))));
            assertTrue(minHeap.get(root).less(minHeap.get(minHeap.right(root))));
            checkMinHeapProperty(minHeap, (IBinaryTreeHeapNode<IKey128>) root.left());
            checkMinHeapProperty(minHeap, (IBinaryTreeHeapNode<IKey128>) root.right());
        }

    }

    @Test
    public void build() {
        BinaryHeap<IBinaryTreeHeapNode<IKey128>> tree = ((BinaryTreeMinHeap) heap).getTree();
        checkMinHeapProperty(tree, tree.root());
        assertTrue(tree.size()!=0);
        assertEquals(tree.last().index(),tree.size()-1);

    }


    //    @Test
//    public void union() {
//        List<IKey128> otherList = Stream.of(32, 10, 40, 25, 60, 1, 4)
//                .map(String::valueOf)
//                .map(IKey128::new)
//                .collect(Collectors.toList());
//        BinaryTreeMinHeap union = (BinaryTreeMinHeap) minHeap.union(new BinaryTreeMinHeap(otherList));
//        checkMinHeapProperty(union.getTree(), union.getTree().root());
//    }
}