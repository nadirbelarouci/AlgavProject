package com.upmc.algav.heap;

import com.upmc.algav.interfaces.BinaryHeap;
import org.junit.Test;

import java.nio.file.Path;

import static org.junit.Assert.assertTrue;


public class ArrayMinHeapTest extends MinHeapTest {


    public ArrayMinHeapTest(Path path) throws Exception {
        super(path);
        heap = new ArrayMinHeap(keys);


    }

    protected void checkMinHeapProperty(BinaryHeap<Integer> minHeap) {
        for (int i = 0; i < minHeap.elements().size(); i++) {
            assertTrue(minHeap.get(i).less(minHeap.get(minHeap.left(i))));
            assertTrue(minHeap.get(i).less(minHeap.get(minHeap.right(i))));
        }

    }


    @Test
    public void build() {
        checkMinHeapProperty(((ArrayMinHeap) heap).getArrayMinHeap());
    }

//    @Test
//    public void union() {
//        List<IKey128> otherList = Stream.of(32, 10, 40, 25, 60, 1, 4)
//                .map(String::valueOf)
//                .map(IKey128::new)
//                .collect(Collectors.toList());
//        ArrayMinHeap union = (ArrayMinHeap) minHeap.union(new ArrayMinHeap(otherList));
//
//        checkMinHeapProperty(union.getArrayMinHeap());
//
//
//    }
}