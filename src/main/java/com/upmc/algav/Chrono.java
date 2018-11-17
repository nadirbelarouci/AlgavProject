package com.upmc.algav;

import java.time.Duration;

public class Chrono {
    public static Duration execute(Runnable block) {
        long time = System.currentTimeMillis();
        block.run();
        time = System.currentTimeMillis() - time;
        return Duration.ofMillis(time);
    }
}
