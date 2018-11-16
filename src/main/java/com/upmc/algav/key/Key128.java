package com.upmc.algav.key;

import java.math.BigInteger;
import java.util.Objects;

public class Key128 implements IKey128<Key128>, Comparable<Key128> {

    private BigInteger value;

    public Key128(String value) {
        this.value = new BigInteger(value, 16);
    }

    public static Key128 min(Key128 first, Key128 second) {
        return first.less(second) ? first : second;
    }

    @Override
    public boolean less(Key128 other) {

        return other == null || value.compareTo(other.value) < 0;
    }

    @Override
    public boolean eq(Key128 other) {
        return value.compareTo(other.value) == 0;
    }

    @Override
    public int compareTo(Key128 key128) {
        if (less(key128))
            return -1;
        else if (eq(key128))
            return 0;
        else
            return 1;
    }

    @Override
    public String toString() {
        return value.toString(16);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key128)) return false;
        Key128 key128 = (Key128) o;
        return Objects.equals(value, key128.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
