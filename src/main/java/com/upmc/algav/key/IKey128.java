package com.upmc.algav.key;

public interface IKey128<T>{
    boolean less(T other);
    boolean eq(T other);
}
