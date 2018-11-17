package com.upmc.algav.heap;

import com.upmc.algav.tree.BinaryTreeNode;
import com.upmc.algav.tree.IBinaryTreeNode;

import java.util.Stack;

class BinaryTreeHeapNode<T> extends BinaryTreeNode<T> {


    private int index;

    public BinaryTreeHeapNode(T key, IBinaryTreeNode<T> left, IBinaryTreeNode<T> right, IBinaryTreeNode<T> parent, int index) {
        super(key, left, right, parent);
        this.index = index;
    }

    private static <T> BinaryTreeHeapNode<T> get(IBinaryTreeNode<T> root, int index) {
        Stack<Integer> path = new Stack<>();
        while (index != 0) {
            path.push(index);
            index = (index - 1) / 2;
        }

        while (!path.empty()) {
            int i = path.pop();
            root = i % 2 == 0 ? root.right() : root.left();
        }
        return (BinaryTreeHeapNode<T>) root;
    }

    public BinaryTreeHeapNode<T> insert(IBinaryTreeNode<T> root, T key) {
        if (left() == null) {
            left(new NodeBuilder<>(key, index * 2 + 1)
                    .setParent(this)
                    .createNode());
            return this;
        } else {
            right(new NodeBuilder<>(key, index * 2 + 2)
                    .setParent(this)
                    .createNode());
            return get(root, index + 1);
        }
    }

    public BinaryTreeHeapNode<T> detach(IBinaryTreeNode<T> root) {
        if (!detach())
            return null;
        return get(root, index - 1);
    }

    static class NodeBuilder<T> {
        private T key;
        private IBinaryTreeNode<T> left;
        private IBinaryTreeNode<T> right;
        private IBinaryTreeNode<T> parent;
        private int index;


        public NodeBuilder(T key, int index) {
            this.key = key;
            this.index = index;
        }

        public NodeBuilder<T> setLeft(IBinaryTreeNode<T> left) {
            this.left = left;
            return this;
        }

        public NodeBuilder<T> setRight(IBinaryTreeNode<T> right) {
            this.right = right;
            return this;
        }

        public NodeBuilder<T> setParent(IBinaryTreeNode<T> parent) {
            this.parent = parent;
            return this;
        }


        public IBinaryTreeNode<T> createNode() {
            return new BinaryTreeHeapNode<>(key, left, right, parent, index);
        }
    }
}

