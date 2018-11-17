package com.upmc.algav.tree;

import java.util.Collection;

public interface IBinaryTree<T> {
    IBinaryTreeNode<T> root();
    void root(IBinaryTreeNode<T> root);

//    boolean insert(T key);

    void insert(IBinaryTreeNode<T> node);

    Collection<T> explode();

}
