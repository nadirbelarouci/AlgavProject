package com.upmc.algav.interfaces;

public interface IKey128 extends Comparable<IKey128> {
    default boolean less(IKey128 other) {
        return other == null || this.compareTo(other) < 0;
    }

    default boolean eq(IKey128 other) {
        return this.compareTo(other) == 0;
    }

}
