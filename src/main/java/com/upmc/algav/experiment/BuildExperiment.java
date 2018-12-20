package com.upmc.algav.experiment;

import com.upmc.algav.key.IKey128;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;


public class BuildExperiment implements Experiment {

    private List<IKey128> keys;
    private Consumer<List<IKey128>> builder;

    public BuildExperiment(List<IKey128> keys, Consumer<List<IKey128>> builder) {
        this.keys = keys;
        this.builder = builder;
    }


    @Override
    public Duration execute() {
        return Experiment.execute(() -> builder.accept(keys));
    }
}
