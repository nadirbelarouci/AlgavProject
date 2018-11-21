package com.upmc.algav.heap;


import com.upmc.algav.heap.BinaryTreeHeapNode.NodeBuilder;
import com.upmc.algav.key.Key128;
import com.upmc.algav.tree.BinaryTree;
import com.upmc.algav.tree.IBinaryTreeNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class BinaryTreeMinHeap implements MinHeap<Key128> {

    private BinaryTreeMinHeapImpl<Key128> tree;

    public BinaryTreeMinHeap(Collection<Key128> values) {
        tree = new BinaryTreeMinHeapImpl<>();
        build(values);
    }


    @Override
    public Key128 deleteMin() {
        if (empty())
            return null;
        if (tree.size == 1) {
            Key128 key128 = tree.root().key();
            tree.size--;
            tree.root(null);
            return key128;
        }
        return Heapable.deleteMin(tree,
                () -> {
                    tree.swap(tree.root(), tree.last);
                    tree.detachLast();
                });
    }


    @Override
    public void insert(Key128 key) {
        tree.insert(key, Key128::less);
    }

    @Override
    public Collection<Key128> elements() {
        return tree.elements();
    }

    @Override
    public Heap<Key128> union(Heap<Key128> other) {
        List<Key128> all = new ArrayList<>();
        all.addAll(elements());
        all.addAll(other.elements());
        return new BinaryTreeMinHeap(all);
    }

    @Override
    public boolean empty() {
        return tree.root() == null;
    }

    @Override
    public String toString() {
        return elements()
                .stream()
                .map(Key128::toString)
                .collect(Collectors.joining(", "));
    }

    BinaryTreeMinHeapImpl<Key128> getTree() {
        return tree;
    }

    static class BinaryTreeMinHeapImpl<T extends Comparable<T>>
            extends BinaryTree<T> implements Heapable<T, IBinaryTreeHeapNode<T>> {

        private IBinaryTreeHeapNode<T> nextToInsert;
        private IBinaryTreeHeapNode<T> last;
        private int size;

        private BinaryTreeHeapNode<T> get(int index) {
            IBinaryTreeNode<T> p = root();
            Stack<Integer> path = new Stack<>();
            while (index != 0) {
                path.push(index);
                index = (index - 1) / 2;
            }

            while (!path.empty()) {
                int i = path.pop();
                p = i % 2 == 0 ? p.right() : p.left();
            }
            return (BinaryTreeHeapNode<T>) p;
        }

        @Override
        public T get(IBinaryTreeHeapNode<T> x) {
            if (x == null)
                return null;
            return x.key();
        }

        @Override
        public void swap(IBinaryTreeHeapNode<T> first, IBinaryTreeHeapNode<T> second) {
            T temp = first.key();
            first.key(second.key());
            second.key(temp);
        }

        @Override
        public IBinaryTreeHeapNode<T> left(IBinaryTreeHeapNode<T> x) {
            return (IBinaryTreeHeapNode<T>) x.left();
        }

        @Override
        public IBinaryTreeHeapNode<T> right(IBinaryTreeHeapNode<T> x) {
            return (IBinaryTreeHeapNode<T>) x.right();
        }

        @Override
        public IBinaryTreeHeapNode<T> parent(IBinaryTreeHeapNode<T> x) {
            return (IBinaryTreeHeapNode<T>) x.parent();
        }

        @Override
        public IBinaryTreeHeapNode<T> last() {
            return last;
        }

        @Override
        public IBinaryTreeHeapNode<T> root() {
            return (IBinaryTreeHeapNode<T>) super.root();
        }

        @Override
        public void insert(IBinaryTreeNode<T> node) {

        }

        public IBinaryTreeHeapNode<T> insert(T key) {
            if (nextToInsert.left() == null) {
                nextToInsert.left(new NodeBuilder<>(key, nextToInsert.index() * 2 + 1)
                        .setParent(nextToInsert)
                        .createNode());
                return nextToInsert;
            } else {
                nextToInsert.right(new NodeBuilder<>(key, nextToInsert.index() * 2 + 2)
                        .setParent(nextToInsert)
                        .createNode());
                return get(nextToInsert.index() + 1);
            }
        }

        public void insert(T key, BiPredicate<T, T> compare) {
            if (root() == null) {
                root(new NodeBuilder<>(key, 0).createNode());
                nextToInsert = last = root();
            } else {
                IBinaryTreeHeapNode<T> temp = insert(key);
                last = (IBinaryTreeHeapNode<T>) (nextToInsert == temp ? nextToInsert.left() :
                        nextToInsert.right());
                nextToInsert = temp;
            }
            size++;
            Heapable.heapifyUp(this, last,
                    k -> k != root(), compare);

        }

        private void detachLast() {
            if (last.detach()) {
                last = get(last.index() - 1);
                size--;
            }
        }

        @Override
        public Collection<T> elements() {
            return explode();
        }
    }


}
