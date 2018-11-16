package com.upmc.algav.heap;

import java.util.Collection;

public interface Heap<T, R> {
    R left(R r);

    R right(R r);

    R parent(R r);

    R root();

    R last();

    T get(R r);

    boolean empty();

    Collection<T> elements();

    void swap(R first, R second);

    void insert(T key);

    default void build(Collection<T> elements) {
        elements.forEach(this::insert);
    }

    Heap<T, R> union(Heap<T, R> other);

}
