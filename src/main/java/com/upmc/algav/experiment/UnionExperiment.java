package com.upmc.algav.experiment;

import com.upmc.algav.heap.MinHeap;

import java.time.Duration;

public class UnionExperiment implements Experiment {
    private MinHeap first;
    private MinHeap second;

    public UnionExperiment(MinHeap first, MinHeap second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public Duration execute() {
        return Experiment.execute(() -> first.union(second));
    }
}
