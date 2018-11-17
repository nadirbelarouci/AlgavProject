package com.upmc.algav.heap;

import java.util.Collection;

public interface Heap<T> {

    boolean empty();

    Collection<T> elements();

    void insert(T key);

    default void build(Collection<T> elements) {
        elements.forEach(this::insert);
    }

    Heap<T> union(Heap<T> other);

}
