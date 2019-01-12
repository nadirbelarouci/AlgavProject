package com.upmc.algav.tree;

import com.upmc.algav.interfaces.IBinaryTreeNode;

public class BinaryTreeNode<T> implements IBinaryTreeNode<T> {
    private T key;
    private IBinaryTreeNode<T> left;
    private IBinaryTreeNode<T> right;
    private IBinaryTreeNode<T> parent;

    public BinaryTreeNode(T key, IBinaryTreeNode<T> left, IBinaryTreeNode<T> right, IBinaryTreeNode<T> parent) {
        this.key = key;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    @Override
    public T key() {
        return key;
    }

    @Override
    public void key(T key) {
        this.key = key;
    }

    @Override
    public void left(IBinaryTreeNode<T> left) {
        this.left = left;
    }

    @Override
    public void right(IBinaryTreeNode<T> right) {
        this.right = right;
    }

    @Override
    public void parent(IBinaryTreeNode<T> parent) {
        this.parent = parent;
    }


    @Override
    public IBinaryTreeNode<T> left() {
        return left;
    }

    @Override
    public IBinaryTreeNode<T> right() {
        return right;
    }

    @Override
    public IBinaryTreeNode<T> parent() {
        return parent;
    }

    @Override
    public boolean detach() {
        if (parent == null)
            return false;

        if (parent.left() == this)
            parent.left(null);
        else
            parent.right(null);
        parent = null;
        return true;
    }


}
