package com.upmc.algav.heap;


import com.upmc.algav.heap.BinaryTreeHeapNode.NodeBuilder;
import com.upmc.algav.key.IKey128;
import com.upmc.algav.tree.BinaryTree;
import com.upmc.algav.tree.IBinaryTreeNode;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class BinaryTreeMinHeap implements MinHeap {

    private BinaryTreeMinHeapImpl tree = new BinaryTreeMinHeapImpl();

    public BinaryTreeMinHeap(List<IKey128> values) {
        build(values);
    }

    @Override
    public IKey128 deleteMin() {

        return tree.deleteMin();
    }


    @Override
    public void insert(IKey128 key) {
        tree.insert(key, IKey128::less);
    }

    @Override
    public void build(List<IKey128> elements) {
        List<IBinaryTreeHeapNode<IKey128>> nodes = tree.addAll(elements);
        for (int i = elements.size() / 2; i >= 0; i--) {
            tree.heapifyDown(nodes.get(i), tree.MIN());
        }
    }

    @Override
    public Collection<IKey128> elements() {
        return tree.elements();
    }

    @Override
    public BinaryTreeMinHeap union(Heap<IKey128> other) {
        List<IKey128> all = new ArrayList<>();
        all.addAll(elements());
        all.addAll(other.elements());
        return new BinaryTreeMinHeap(all);
    }

    @Override
    public boolean isEmpty() {
        return tree.empty();
    }

    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public String toString() {
        return elements()
                .stream()
                .map(IKey128::toString)
                .collect(Collectors.joining(", "));
    }

    BinaryTreeMinHeapImpl getTree() {
        return tree;
    }

    static class BinaryTreeMinHeapImpl
            extends BinaryTree<IKey128> implements BinaryHeap<IBinaryTreeHeapNode<IKey128>> {

        private IBinaryTreeHeapNode<IKey128> nextToInsert;
        private IBinaryTreeHeapNode<IKey128> last;
        private int size;

        private BinaryTreeHeapNode<IKey128> getNode(int index) {
            IBinaryTreeNode<IKey128> p = root();
            Stack<Integer> path = new Stack<>();
            while (index != 0) {
                path.push(index);
                index = (index - 1) / 2;
            }

            while (!path.empty()) {
                int i = path.pop();
                p = i % 2 == 0 ? p.right() : p.left();
            }
            return (BinaryTreeHeapNode<IKey128>) p;
        }

        @Override
        public IKey128 get(IBinaryTreeHeapNode<IKey128> x) {
            if (x == null)
                return null;
            return x.key();
        }

        @Override
        public void swap(IBinaryTreeHeapNode<IKey128> first, IBinaryTreeHeapNode<IKey128> second) {
            IKey128 temp = first.key();
            first.key(second.key());
            second.key(temp);
        }

        @Override
        public IBinaryTreeHeapNode<IKey128> left(IBinaryTreeHeapNode<IKey128> x) {
            return (IBinaryTreeHeapNode<IKey128>) x.left();
        }

        @Override
        public IBinaryTreeHeapNode<IKey128> right(IBinaryTreeHeapNode<IKey128> x) {
            return (IBinaryTreeHeapNode<IKey128>) x.right();
        }

        @Override
        public IBinaryTreeHeapNode<IKey128> parent(IBinaryTreeHeapNode<IKey128> x) {
            return (IBinaryTreeHeapNode<IKey128>) x.parent();
        }

        @Override
        public IBinaryTreeHeapNode<IKey128> last() {
            return last;
        }

        @Override
        public IBinaryTreeHeapNode<IKey128> root() {
            return (IBinaryTreeHeapNode<IKey128>) super.root();
        }

        @Override
        public void insert(IBinaryTreeNode<IKey128> node) {

        }

        private IBinaryTreeHeapNode<IKey128> insert(IKey128 key) {
            if (nextToInsert.left() == null) {
                nextToInsert.left(new NodeBuilder<>(key, nextToInsert.index() * 2 + 1)
                        .setParent(nextToInsert)
                        .createNode());
                return nextToInsert;
            } else {
                nextToInsert.right(new NodeBuilder<>(key, nextToInsert.index() * 2 + 2)
                        .setParent(nextToInsert)
                        .createNode());
                return getNode(nextToInsert.index() + 1);
            }
        }

        private void insert(IKey128 key, BiPredicate<IKey128, IKey128> compare) {
            if (root() == null) {
                root(new NodeBuilder<>(key, 0).createNode());
                nextToInsert = last = root();
            } else {
                IBinaryTreeHeapNode<IKey128> temp = insert(key);
                last = (IBinaryTreeHeapNode<IKey128>) (nextToInsert == temp ? nextToInsert.left() :
                        nextToInsert.right());
                nextToInsert = temp;
            }
            size++;
            heapifyUp(last, k -> k != root(), compare);

        }


        @Override
        public Collection<IKey128> elements() {
            return explode();
        }

        @Override
        public void swapAndRemoveLast() {
            swap(root(), last);
            nextToInsert = parent(last);
            if (last.detach()) {
                last = getNode(last.index() - 1);
                size--;
            } else {
                root(null);
            }
        }

        private List<IBinaryTreeHeapNode<IKey128>> addAll(List<IKey128> elements) {

            List<IBinaryTreeHeapNode<IKey128>> nodes = new ArrayList<>();
            ListIterator<IKey128> it = elements.listIterator();
            while (it.hasNext()) {
                IBinaryTreeHeapNode<IKey128> node = createNode(it);
                if (node.index() == 0) {
                    root(node);
                } else {
                    IBinaryTreeHeapNode<IKey128> parent = nodes.get((node.index() - 1) / 2);
                    if (node.index() % 2 == 0) {
                        parent.right(node);
                    } else {
                        parent.left(node);
                    }
                    node.parent(parent);
                }
                nodes.add(node);
            }
            last = nodes.get(elements.size() - 1);
            size = nodes.size();
            return nodes;
        }

        private IBinaryTreeHeapNode<IKey128> createNode(ListIterator<IKey128> it) {

            int i = it.nextIndex();
            IKey128 e = it.next();
            return new NodeBuilder<>(e, i).createNode();
        }

        @Override
        public boolean empty() {
            return size == 0;
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public void remove(IBinaryTreeHeapNode<IKey128> i) {
            if (!i.detach())
                root(null);
            size--;

        }
    }


}
