package com.upmc.algav.heap;

import com.upmc.algav.tree.BinaryTreeNode;
import com.upmc.algav.tree.IBinaryTreeNode;

class BinaryTreeHeapNode<T> extends BinaryTreeNode<T> implements IBinaryTreeHeapNode<T> {

    private int index;

    private BinaryTreeHeapNode(T key, IBinaryTreeNode<T> left, IBinaryTreeNode<T> right, IBinaryTreeNode<T> parent, int index) {
        super(key, left, right, parent);
        this.index = index;
    }
    @Override
    public int index() {
        return index;
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

