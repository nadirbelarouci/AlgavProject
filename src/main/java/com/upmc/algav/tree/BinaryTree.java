package com.upmc.algav.tree;


import java.util.*;

public abstract class BinaryTree<T> implements IBinaryTree<T> {
    private IBinaryTreeNode<T> root;

    @Override
    public IBinaryTreeNode<T> root() {
        return root;
    }

    @Override
    public void root(IBinaryTreeNode<T> root) {
        this.root = root;
    }

    @Override
    public Collection<T> explode() {
        Queue<IBinaryTreeNode<T>> queue = new LinkedList<>();
        queue.add(root);
        List<T> set = new ArrayList<>();
        while (!queue.isEmpty()) {
            IBinaryTreeNode<T> temp = queue.remove();
            if (temp == null)
                continue;
            set.add(temp.key());
            queue.add(temp.left());
            queue.add(temp.right());
        }
        return set;
    }

    @Override
    public String toString() {
        return explode().toString();
    }
}
