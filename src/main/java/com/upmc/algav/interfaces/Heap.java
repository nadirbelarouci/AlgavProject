package com.upmc.algav.interfaces;

import java.util.Collection;
import java.util.List;

public interface Heap<T> {

    boolean isEmpty();

    Collection<T> elements();

    void insert(T key);

    default void build(List<T> elements) {
        elements.forEach(this::insert);
    }

    Heap<T> union(Heap<T> other);

    int size();

}
