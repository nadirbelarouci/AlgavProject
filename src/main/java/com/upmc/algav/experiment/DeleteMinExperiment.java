package com.upmc.algav.experiment;

import com.upmc.algav.heap.MinHeap;
import com.upmc.algav.key.Key128;

import java.time.Duration;

public class DeleteMinExperiment implements Experiment {
    private MinHeap<Key128> heap;

    public DeleteMinExperiment(MinHeap<Key128> heap) {
        this.heap = heap;
    }

    @Override
    public Duration execute() {
        return Experiment.execute(() -> heap.deleteMin());
    }
}
