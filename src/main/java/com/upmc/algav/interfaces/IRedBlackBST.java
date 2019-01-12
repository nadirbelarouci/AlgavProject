package com.upmc.algav.interfaces;

import com.upmc.algav.tree.RedBlackBSTNode;

public interface IRedBlackBST<T> extends IBinaryTree<T> {
    IRedBlackBSTNode<T> search(T key);

    @Override
    IRedBlackBSTNode<T> root();

    default void insert(T key) {
        IRedBlackBSTNode<T> node = new RedBlackBSTNode<>();
        node.key(key);
        insert(node);

    }


}
