package com.upmc.algav.tree;

import com.upmc.algav.interfaces.IBinaryTreeNode;
import com.upmc.algav.interfaces.IRedBlackBST;
import com.upmc.algav.interfaces.IRedBlackBSTNode;
import com.upmc.algav.interfaces.IKey128;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.upmc.algav.interfaces.IRedBlackBSTNode.Color.BLACK;
import static com.upmc.algav.interfaces.IRedBlackBSTNode.Color.RED;

public class RedBlackBST<T extends IKey128> extends BinaryTree<T> implements IRedBlackBST<T> {

    private int size;

    @SuppressWarnings("unchecked")
    public RedBlackBST() {


    }

    @Override
    public IRedBlackBSTNode<T> search(T key) {
        IRedBlackBSTNode<T> x = root();
        while (x != null && !key.equals(x.key())) {
            if (key.less(x.key()))
                x = x.left();
            else
                x = x.right();

        }
        return x;
    }

    @Override
    public IRedBlackBSTNode<T> root() {
        return (IRedBlackBSTNode<T>) super.root();
    }

    @SuppressWarnings("unchecked")
    @Override
    public synchronized void insert(IBinaryTreeNode<T> z) {
        IRedBlackBSTNode<T> t = root();
        if (t == null) {
            root(z);
            root().color(BLACK);
            size = 1;
            return;
        }
        int cmp;
        IRedBlackBSTNode<T> parent;

        if (z.key() == null)
            throw new NullPointerException();

        do {
            parent = t;
            cmp = z.key().compareTo(t.key());
            if (cmp < 0)
                t = t.left();
            else if (cmp > 0)
                t = t.right();
            else
                return;
        } while (t != null);


        z.parent(parent);


        if (cmp < 0)
            parent.left(z);
        else
            parent.right(z);
        insertFixUp((IRedBlackBSTNode<T>) z);
        size++;

    }

    private void insertFixUp(IRedBlackBSTNode<T> x) {
        x.color(RED);
        while (x != null && x != root() && x.parent().color() == RED) {
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                IRedBlackBSTNode<T> y = rightOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == rightOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateLeft(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateRight(parentOf(parentOf(x)));
                }
            } else {
                IRedBlackBSTNode<T> y = leftOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == leftOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateRight(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateLeft(parentOf(parentOf(x)));
                }
            }
        }
        root().color(BLACK);
    }

    private void setColor(IRedBlackBSTNode<T> x, IRedBlackBSTNode.Color c) {
        if (x != null)
            x.color(c);
    }

    private IRedBlackBSTNode<T> rightOf(IRedBlackBSTNode<T> x) {
        return x == null ? null : x.right();

    }

    private IRedBlackBSTNode.Color colorOf(IRedBlackBSTNode<T> y) {
        return y == null ? BLACK : y.color();
    }

    private IRedBlackBSTNode<T> leftOf(IRedBlackBSTNode<T> x) {
        return x == null ? null : x.left();
    }


    private IRedBlackBSTNode<T> parentOf(IRedBlackBSTNode<T> x) {
        return x == null ? null : x.parent();
    }


    private void rotateLeft(IRedBlackBSTNode<T> p) {

        if (p != null) {
            IRedBlackBSTNode<T> r = p.right();
            p.right(r.left());
            if (r.left() != null)
                r.left().parent(p);
            r.parent(p.parent());
            if (p.parent() == null)
                root(r);
            else if (p.parent().left() == p)
                p.parent().left(r);
            else
                p.parent().right(r);
            r.left(p);
            p.parent(r);
        }
    }

    private void rotateRight(IRedBlackBSTNode<T> p) {
        if (p != null) {
            IRedBlackBSTNode<T> l = p.left();
            p.left(l.right());
            if (l.right() != null) l.right().parent(p);
            l.parent(p.parent());
            if (p.parent() == null)
                root(l);
            else if (p.parent().right() == p)
                p.parent().right(l);
            else p.parent().left(l);
            l.right(p);
            p.parent(l);
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

    public int size() {
        return size;
    }
}

