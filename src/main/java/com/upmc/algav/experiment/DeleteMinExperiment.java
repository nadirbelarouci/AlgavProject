package com.upmc.algav.experiment;

import com.upmc.algav.heap.MinHeap;

import java.time.Duration;

public class DeleteMinExperiment implements Experiment {
    private MinHeap heap;

    public DeleteMinExperiment(MinHeap heap) {
        this.heap = heap;
    }

    @Override
    public Duration execute() {
        return Experiment.execute(() -> heap.deleteMin());
    }
}
