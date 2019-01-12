package com.upmc.algav.tree;

public class RedBlackBSTNode<T> extends BinaryTreeNode<T> implements IRedBlackBSTNode<T> {
    private Color color;

    public RedBlackBSTNode(T key, IBinaryTreeNode<T> left, IBinaryTreeNode<T> right, IBinaryTreeNode<T> parent) {
        super(key, left, right, parent);
    }

    public RedBlackBSTNode() {
        super(null, null, null, null);
        this.color = Color.BLACK;
    }

    @Override
    public Color color() {
        return color;
    }

    @Override
    public void color(Color color) {
        this.color = color;
    }

    @Override
    public IRedBlackBSTNode<T> left() {
        return (IRedBlackBSTNode<T>) super.left();
    }

    @Override
    public IRedBlackBSTNode<T> right() {
        return (IRedBlackBSTNode<T>) super.right();
    }

    @Override
    public IRedBlackBSTNode<T> parent() {
        return (IRedBlackBSTNode<T>) super.parent();
    }



}
