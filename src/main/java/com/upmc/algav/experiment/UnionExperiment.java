package com.upmc.algav.experiment;

import com.upmc.algav.heap.MinHeap;
import com.upmc.algav.key.Key128;

import java.time.Duration;

public class UnionExperiment implements Experiment {
    private MinHeap<Key128> first;
    private MinHeap<Key128> second;

    public UnionExperiment(MinHeap<Key128> first, MinHeap<Key128> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public Duration execute() {
        return Experiment.execute(() -> first.union(second));
    }
}
