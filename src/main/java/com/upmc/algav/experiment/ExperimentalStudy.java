package com.upmc.algav.experiment;

import com.upmc.algav.heap.ArrayMinHeap;
import com.upmc.algav.heap.BinaryTreeMinHeap;
import com.upmc.algav.heap.BinomialMinHeap;
import com.upmc.algav.heap.MinHeap;
import com.upmc.algav.key.Key128;

import java.time.Duration;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.function.Consumer;

public class ExperimentalStudy {


    public static Duration doBuildExperiment(Collection<Key128> data, String heap) {
        Consumer<Collection<Key128>> builder = null;
        switch (heap) {
            case "ArrayMinHeap":
                builder = ArrayMinHeap::new;
                break;
            case "BinaryTreeMinHeap":
                builder = BinaryTreeMinHeap::new;
                break;
            case "BinomialMinHeap":
                builder = BinomialMinHeap::new;
                break;
            case "JavaAPI":
                builder = PriorityQueue::new;
                break;
            default:
                throw new IllegalArgumentException((heap + " is not a valid Min Heap implementation!"));
        }

        return new BuildExperiment(data, builder).execute();
    }

    public static Duration doInsertExpirement(MinHeap<Key128> heap, Key128 key128) {
        if (key128 == null)
            throw new IllegalArgumentException("key must not be null");

        if (heap == null)
            throw new IllegalArgumentException("heap must not be null");

        return new InsertExperiment(heap, key128).execute();
    }

    public static Duration doUnionExpirement(MinHeap<Key128> first, MinHeap<Key128> second) {
        if (first == null || second == null)
            throw new IllegalArgumentException("heap must not be null");


        return new UnionExperiment(first, second).execute();
    }

    public static Duration doDeleteMinExpirement(MinHeap<Key128> heap) {

        if (heap == null)
            throw new IllegalArgumentException("heap must not be null");

        return new DeleteMinExperiment(heap).execute();
    }


}
