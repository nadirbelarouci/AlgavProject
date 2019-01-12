package com.upmc.algav.tree;

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
