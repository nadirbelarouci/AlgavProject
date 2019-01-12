package com.upmc.algav.experiment;

import com.upmc.algav.heap.ArrayMinHeap;
import com.upmc.algav.heap.BinaryTreeMinHeap;
import com.upmc.algav.heap.BinomialMinHeap;
import com.upmc.algav.heap.MinHeap;
import com.upmc.algav.key.IKey128;

import java.time.Duration;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Consumer;

public class ExperimentalStudy {
    @SuppressWarnings("unchecked")
    public static Duration doExperiment(String experiment, Object... args) {
        switch (experiment) {
            case "build":
                if (args.length != 2)
                    throw new IllegalArgumentException("Args for " + experiment + " are : keys:List<Keys>, heapType:String");
                return doBuildExperiment((List<IKey128>) args[0], (String) args[1]);
            case "insert":
                if (args.length != 2)
                    throw new IllegalArgumentException("Args for " + experiment + " are : heap:MinHeap, key:IKey128");
                return doInsertExpirement((MinHeap) args[0], (IKey128) args[1]);
            case "union":
                if (args.length != 2)
                    throw new IllegalArgumentException("Args for " + experiment + " are : first:MinHeap, second:MinHeap");
                return doUnionExpirement((MinHeap) args[0], (MinHeap) args[1]);
            case "deleteMin":
                if (args.length != 1)
                    throw new IllegalArgumentException("Args for " + experiment + " are : heap:MinHeap");
                return doDeleteMinExpirement((MinHeap) args[0]);
            default:
                throw new IllegalArgumentException((experiment + " is not a valid experiment!"));

        }
    }

    private static Duration doBuildExperiment(List<IKey128> data, String heap) {
        Consumer<List<IKey128>> builder = null;
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

    private static Duration doInsertExpirement(MinHeap heap, IKey128 key128) {
        if (key128 == null)
            throw new IllegalArgumentException("key must not be null");

        if (heap == null)
            throw new IllegalArgumentException("heap must not be null");

        return new InsertExperiment(heap, key128).execute();
    }

    private static Duration doUnionExpirement(MinHeap first, MinHeap second) {
        if (first == null || second == null)
            throw new IllegalArgumentException("heap must not be null");


        return new UnionExperiment(first, second).execute();
    }

    private static Duration doDeleteMinExpirement(MinHeap heap) {

        if (heap == null)
            throw new IllegalArgumentException("heap must not be null");

        return new DeleteMinExperiment(heap).execute();
    }


}
