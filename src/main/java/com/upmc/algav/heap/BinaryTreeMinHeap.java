package com.upmc.algav.heap;

import com.upmc.algav.heap.Node.NodeBuilder;
import com.upmc.algav.key.Key128;

import java.util.*;
import java.util.stream.Collectors;

public class BinaryTreeMinHeap implements MinHeap<Key128, Node<Key128>> {

    private Node<Key128> root;
    private Node<Key128> nextToInsert;
    private Node<Key128> last;
    private int size;

    public BinaryTreeMinHeap(Collection<Key128> values) {
        build(values);
    }

    @Override
    public Key128 get(Node<Key128> key128Node) {
        if (key128Node == null)
            return null;
        return key128Node.data;
    }

    @Override
    public void swap(Node<Key128> first, Node<Key128> second) {
        Key128 temp = first.data;
        first.data = second.data;
        second.data = temp;
    }

    @Override
    public Node<Key128> left(Node<Key128> node) {
        return node.left;
    }

    @Override
    public Node<Key128> right(Node<Key128> node) {
        return node.right;
    }

    @Override
    public Node<Key128> parent(Node<Key128> node) {
        return node.parent;
    }

    @Override
    public Node<Key128> root() {
        return root;
    }

    @Override
    public Node<Key128> last() {
        return last;
    }


    @Override
    public Key128 deleteMin() {
        if (empty())
            return null;
        if (size == 1) {
            Key128 key128 = root.data;
            size--;
            root = null;
            return key128;
        }
        return MinHeap.deleteMin(this,
                () -> {
                    swap(root, last);
                    Node<Key128> temp = last.remove(root);
                    if (temp != null) {
                        last = temp;
                        size--;
                    }
                });
    }

    @Override
    public void insert(Key128 key) {
        if (root == null) {
            root = new NodeBuilder<>(key, 0).createNode();
            last = root;
            nextToInsert = root;
        } else {
            Node<Key128> node = nextToInsert.insert(root, key);
            last = nextToInsert == node ? nextToInsert.left : nextToInsert.right;
            nextToInsert = node;
        }
        size++;

        MinHeap.minHeapifyUp(this, last,
                k -> k != root, Key128::less);
    }

    @Override
    public Collection<Key128> elements() {
        Queue<Node<Key128>> queue = new LinkedList<>();
        queue.add(root);
        Set<Key128> set = new HashSet<>();
        while (!queue.isEmpty()) {
            Node<Key128> temp = queue.remove();
            if (temp == null)
                continue;
            set.add(temp.data);
            queue.add(temp.left);
            queue.add(temp.right);
        }
        return set;
    }

    @Override
    public Heap<Key128, Node<Key128>> union(Heap<Key128, Node<Key128>> other) {
        List<Key128> all = new ArrayList<>();
        all.addAll(elements());
        all.addAll(other.elements());
        return new BinaryTreeMinHeap(all);
    }

    @Override
    public boolean empty() {
        return root == null;
    }

    @Override
    public String toString() {
        return elements()
                .stream()
                .map(Key128::toString)
                .collect(Collectors.joining(", "));
    }


}
