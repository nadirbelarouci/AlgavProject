package com.upmc.algav.experiment;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.time.Duration;

public interface Experiment {
    static Duration execute(Runnable block) {

        long time = getCpuTime();
        block.run();
        time = getCpuTime() - time;
        return Duration.ofNanos(time);
    }


    /**
     * Get CPU time in nanoseconds.
     */
    static long getCpuTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ?
                bean.getCurrentThreadCpuTime() : 0L;
    }

    Duration execute();
}
