package com.upmc.algav.experiment;

import com.upmc.algav.interfaces.MinHeap;
import com.upmc.algav.interfaces.IKey128;

import java.time.Duration;

public class InsertExperiment implements Experiment {
    private IKey128 key;
    private MinHeap heap;

    public InsertExperiment(MinHeap heap, IKey128 key) {
        this.key = key;
        this.heap = heap;
    }

    @Override
    public Duration execute() {
        return Experiment.execute(() -> heap.insert(key));
    }
}
