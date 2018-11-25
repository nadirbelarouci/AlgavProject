package com.upmc.algav.experiment;

import com.upmc.algav.heap.MinHeap;
import com.upmc.algav.key.Key128;

import java.time.Duration;

public class InsertExperiment implements Experiment {
    private Key128 key;
    private MinHeap<Key128> heap;

    public InsertExperiment(MinHeap<Key128> heap, Key128 key) {
        this.key = key;
        this.heap = heap;
    }

    @Override
    public Duration execute() {
        return Experiment.execute(() -> heap.insert(key));
    }
}
