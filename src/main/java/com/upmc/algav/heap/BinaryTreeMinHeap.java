package com.upmc.algav.heap;


import com.upmc.algav.heap.BinaryTreeHeapNode.NodeBuilder;
import com.upmc.algav.key.Key128;
import com.upmc.algav.tree.BinaryTree;
import com.upmc.algav.tree.IBinaryTreeNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BinaryTreeMinHeap extends BinaryTree<Key128> implements MinHeap<Key128, IBinaryTreeNode<Key128>> {


    private IBinaryTreeNode<Key128> nextToInsert;
    private IBinaryTreeNode<Key128> last;
    private int size;

    public BinaryTreeMinHeap(Collection<Key128> values) {
        build(values);
    }

    @Override
    public Key128 get(IBinaryTreeNode<Key128> x) {
        if (x == null)
            return null;
        return x.key();
    }

    @Override
    public void swap(IBinaryTreeNode<Key128> first, IBinaryTreeNode<Key128> second) {
        Key128 temp = first.key();
        first.key(second.key());
        second.key(temp);
    }

    @Override
    public IBinaryTreeNode<Key128> left(IBinaryTreeNode<Key128> x) {
        return x.left();
    }

    @Override
    public IBinaryTreeNode<Key128> right(IBinaryTreeNode<Key128> x) {
        return x.right();
    }

    @Override
    public IBinaryTreeNode<Key128> parent(IBinaryTreeNode<Key128> x) {
        return x.parent();
    }


    @Override
    public IBinaryTreeNode<Key128> last() {
        return last;
    }


    @Override
    public Key128 deleteMin() {
        if (empty())
            return null;
        if (size == 1) {
            Key128 key128 = root().key();
            size--;
            root(null);
            return key128;
        }
        return MinHeap.deleteMin(this,
                () -> {
                    swap(root(), last);
                    BinaryTreeHeapNode<Key128> temp = ((BinaryTreeHeapNode<Key128>) last).detach(root());
                    if (temp != null) {
                        last = temp;
                        size--;
                    }
                });
    }

    @Override
    public void insert(IBinaryTreeNode<Key128> node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(IBinaryTreeNode<Key128> node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insert(Key128 key) {
        if (root() == null) {
            root(new NodeBuilder<>(key, 0).createNode());
            nextToInsert = last = root();
        } else {
            BinaryTreeHeapNode<Key128> temp = ((BinaryTreeHeapNode<Key128>) nextToInsert).insert(root(), key);
            last = nextToInsert == temp ? nextToInsert.left() :
                    nextToInsert.right();
            nextToInsert = temp;
        }
        size++;
        MinHeap.minHeapifyUp(this, last,
                k -> k != root(), Key128::less);

    }

    @Override
    public Collection<Key128> elements() {
        return explode();
    }

    @Override
    public Heap<Key128, IBinaryTreeNode<Key128>> union(Heap<Key128, IBinaryTreeNode<Key128>> other) {
        List<Key128> all = new ArrayList<>();
        all.addAll(elements());
        all.addAll(other.elements());
        return new BinaryTreeMinHeap(all);
    }

    @Override
    public boolean empty() {
        return root() == null;
    }

    @Override
    public String toString() {
        return elements()
                .stream()
                .map(Key128::toString)
                .collect(Collectors.joining(", "));
    }


}
