package com.upmc.algav.tree;

public interface IBinarySearchTree<T> extends IBinaryTree<T> {
    IBinaryTreeNode<T> search(T key);

    default IBinaryTreeNode<T> min() {
        return min(root());
    }

    IBinaryTreeNode<T> min(IBinaryTreeNode<T> x);

    default IBinaryTreeNode<T> max() {
        return max(root());
    }

    IBinaryTreeNode<T> max(IBinaryTreeNode<T> x);

    IBinaryTreeNode<T> successor(IBinaryTreeNode<T> node);

    IBinaryTreeNode<T> predecessor(IBinaryTreeNode<T> node);


}
