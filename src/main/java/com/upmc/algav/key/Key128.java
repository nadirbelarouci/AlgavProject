package com.upmc.algav.key;

import java.math.BigInteger;
import java.util.Objects;

public class Key128 implements IKey128 {

    private BigInteger value;

    public Key128(String value) {
        this.value = new BigInteger(value, 16);
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

    @Override
    public int compareTo(IKey128 o) {
        return value.compareTo(((Key128) o).value);
    }
}
