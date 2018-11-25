package com.upmc.algav.experiment;

import com.upmc.algav.key.Key128;

import java.time.Duration;
import java.util.Collection;
import java.util.function.Consumer;

public class BuildExperiment implements Experiment {
    private Collection<Key128> keys;
    private Consumer<Collection<Key128>> builder;

    public BuildExperiment(Collection<Key128> keys, Consumer<Collection<Key128>> builder) {
        this.keys = keys;
        this.builder = builder;
    }


    @Override
    public Duration execute() {
        return Experiment.execute(() -> builder.accept(keys));
    }
}
