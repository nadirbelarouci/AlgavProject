package com.upmc.algav.heap;

import com.upmc.algav.tree.IBinaryTreeNode;

interface IBinaryTreeHeapNode<T> extends IBinaryTreeNode<T> {
    int index();
}
