package com.upmc.algav.heap;

import java.util.Stack;

class Node<T> {
    int index;
    T data;
    Node<T> left;
    Node<T> right;
    Node<T> parent;

    private Node(T data, int index, Node<T> left, Node<T> right, Node<T> parent) {
        this.data = data;
        this.index = index;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    Node<T> insert(Node<T> root, T data) {
        if (left == null) {
            left = new NodeBuilder<>(data, index * 2 + 1)
                    .setParent(this)
                    .createNode();
            return this;
        } else {
            right = new NodeBuilder<>(data, index * 2 + 2)
                    .setParent(this)
                    .createNode();
            return get(root, index + 1);
        }
    }

    private Node<T> get(Node<T> root, int index) {
        Stack<Integer> path = new Stack<>();
        while (index != 0) {
            path.push(index);
            index = (index - 1) / 2;
        }

        while (!path.empty()) {
            int i = path.pop();
            root = i % 2 == 0 ? root.right : root.left;
        }

        return root;
    }

    Node<T> remove(Node<T> root) {
        if (parent == null)
            return null;

        if (parent.left == this)
            parent.left = null;
        else
            parent.right = null;
        parent = null;
        return get(root, index - 1);
    }

    static class NodeBuilder<N> {
        private N data;
        private int index;
        private Node<N> left;
        private Node<N> right;
        private Node<N> parent;

        public NodeBuilder(N data, int index) {
            this.data = data;
            this.index = index;
        }


        public NodeBuilder<N> setLeft(Node<N> left) {
            this.left = left;
            return this;
        }

        public NodeBuilder<N> setRight(Node<N> right) {
            this.right = right;
            return this;
        }

        public NodeBuilder<N> setParent(Node<N> parent) {
            this.parent = parent;
            return this;
        }

        public Node<N> createNode() {
            return new Node<>(data, index, left, right, parent);
        }
    }
}

