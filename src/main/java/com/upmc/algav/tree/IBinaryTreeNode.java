package com.upmc.algav.tree;

public interface IBinaryTreeNode<T> {
    void key(T key);

    void left(IBinaryTreeNode<T> left);

    void right(IBinaryTreeNode<T> right);

    void parent(IBinaryTreeNode<T> parent);

    T key();

    IBinaryTreeNode<T> left();

    IBinaryTreeNode<T> right();

    IBinaryTreeNode<T> parent();

    boolean detach();

//    IBinaryTreeNode<T> detach(IBinaryTreeNode<T> root);

}
