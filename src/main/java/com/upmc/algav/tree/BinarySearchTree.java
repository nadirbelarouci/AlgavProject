package com.upmc.algav.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BinarySearchTree<T extends Comparable<T>> extends BinaryTree<T> implements IBinarySearchTree<T> {
    @Override
    public IBinaryTreeNode<T> search(T key) {
        IBinaryTreeNode<T> x = root();
        while (x != null && !key.equals(x.key())) {
            if (key.compareTo(x.key()) < 0)
                x = x.left();
            else
                x = x.right();

        }
        return x;
    }


    @Override
    public IBinaryTreeNode<T> min(IBinaryTreeNode<T> x) {
        while (x.left() != null)
            x = x.left();
        return x;
    }

    @Override
    public IBinaryTreeNode<T> max(IBinaryTreeNode<T> x) {
        while (x.right() != null)
            x = x.right();
        return x;
    }

    @Override
    public IBinaryTreeNode<T> successor(IBinaryTreeNode<T> x) {
        if (x.right() != null)
            return min(x.right());
        IBinaryTreeNode<T> p = x.parent();
        while (p != null && x == p.right()) {
            x = p;
            p = p.parent();
        }
        return p;
    }

    @Override
    public IBinaryTreeNode<T> predecessor(IBinaryTreeNode<T> x) {
        if (x.left() != null)
            return max(x.left());
        IBinaryTreeNode<T> p = x.parent();
        while (p != null && x == p.left()) {
            x = p;
            p = p.parent();
        }
        return p;
    }

    @Override
    public void insert(IBinaryTreeNode<T> z) {
        IBinaryTreeNode<T> p = null;
        IBinaryTreeNode<T> x = root();
        while (x != null) {
            p = x;
            if (z.key().compareTo(x.key()) == 0)
                return;
            else if (z.key().compareTo(x.key()) < 0)
                x = x.left();
            else
                x = x.right();
        }
        z.parent(p);
        if (p == null)
            root(z);
        else if (z.key().compareTo(p.key()) < 0)
            p.left(z);
        else
            p.right(z);
    }

    @Override
    public void delete(IBinaryTreeNode<T> z) {
        if (z.left() == null)
            transplant(z, z.right());
        else if (z.right() == null)
            transplant(z, z.left());
        else {
            IBinaryTreeNode<T> y = min(z.right());
            if (y.parent() != z) {
                transplant(y, y.right());
                y.right(z.right());
                y.right().parent(y);
            }
            transplant(z, y);
            y.left(z.left());
            y.left().parent(y);
        }
    }


    private void transplant(IBinaryTreeNode<T> u, IBinaryTreeNode<T> v) {
        if (u.parent() == null)
            root(v);
        else if (u == u.parent().left())
            u.parent().left(v);
        else u.parent().right(v);
        if (v != null) {
            v.parent(u.parent());
        }
    }

    @Override
    public Collection<T> explode() {
        List<T> keys = new ArrayList<>();
        inorderTreeWalk(keys, root());
        return keys;
    }

    private void inorderTreeWalk(List<T> list, IBinaryTreeNode<T> x) {
        if (x != null) {
            inorderTreeWalk(list, x.left());
            list.add(x.key());
            inorderTreeWalk(list, x.right());
        }
    }
}

