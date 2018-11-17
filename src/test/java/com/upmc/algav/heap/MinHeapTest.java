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

    public static void deleteMinTest(MinHeap<Key128, ?> minHeap, Runnable heapProperty) {
        Stream.of("10", "15", "30", "40", "40", "50", "100").map(Key128::new)
                .forEach(key -> {
                    assertEquals(key, minHeap.deleteMin());
                    System.out.println(minHeap);
//                    heapProperty.run();
                });

        assertTrue(minHeap.empty());
        assertNull(minHeap.deleteMin());
    }


}
