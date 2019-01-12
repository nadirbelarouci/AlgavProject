package com.upmc.algav.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tree234<T extends Comparable<T>> {
    private Node<T> root = new Node<>();

    public Tree234(Collection<T> collection) {
        collection.forEach(this::insert);
    }

    public Tree234() {

    }

    public int find(T key) {
        Node<T> curNode = root;
        int childNumber;
        while (true) {
            if ((childNumber = curNode.findItem(key)) != -1)
                return childNumber;
            else if (curNode.isLeaf())
                return -1;
            else
                curNode = getNextChild(curNode, key);
        }
    }


    public void insert(T key) {
        Node<T> curNode = root;
        T tempItem = key;
        while (true) {
            if (curNode.isFull()) {
                split(curNode);
                curNode = curNode.getParent();
                curNode = getNextChild(curNode, key);
            } else if (curNode.isLeaf())
                break;
            else
                curNode = getNextChild(curNode, key);
        }

        curNode.insertItem(tempItem);
    }


    private void split(Node<T> thisNode) {

        T itemB, itemC;
        Node<T> parent, child2, child3;
        int itemIndex;

        itemC = thisNode.removeItem();
        itemB = thisNode.removeItem();
        child2 = thisNode.disconnectChild(2);
        child3 = thisNode.disconnectChild(3);

        Node<T> newRight = new Node<>();

        if (thisNode == root) {
            root = new Node<>();
            parent = root;
            root.connectChild(0, thisNode);
        } else
            parent = thisNode.getParent();


        itemIndex = parent.insertItem(itemB);
        int n = parent.getNumItems();

        for (int j = n - 1; j > itemIndex; j--) {
            Node<T> temp = parent.disconnectChild(j);
            parent.connectChild(j + 1, temp);
        }

        parent.connectChild(itemIndex + 1, newRight);


        newRight.insertItem(itemC);
        newRight.connectChild(0, child2);
        newRight.connectChild(1, child3);
    }


    private Node<T> getNextChild(Node<T> theNode, T theValue) {

        int j;

        int numItems = theNode.getNumItems();
        for (j = 0; j < numItems; j++) {
            if (theValue.compareTo(theNode.getItem(j)) < 0)
                return theNode.getChild(j);
        }
        return theNode.getChild(j);
    }


    private void inorder(Node<T> node, List<T> data) {
        if (node == null)
            return;
        inorder(node.getChild(0), data);
        int items = node.getNumItems();
        for (int j = 0; j < items; j++) {
            data.add(node.getItem(j));
            inorder(node.getChild(j + 1), data);
        }
    }

    public List<T> sort() {
        List<T> list = new ArrayList<>();
        inorder(root, list);
        return list;
    }

    public T min() {
        return getMin(root);
    }

    private T getMin(Node<T> node) {
        if (node.getChild(0) == null)
            return node.getItem(0);
        return getMin(node.getChild(0));
    }


    private class Node<T extends Comparable<T>> {
        private static final int ORDER = 4;
        private int numItems;
        private Node<T> parent;
        private List<Node<T>> childArray = new ArrayList<>(Collections.nCopies(ORDER, null));
        private Object itemArray[] = new Object[ORDER - 1];

        public void displayNode() {
            String s = Stream.of(itemArray)
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            System.out.println("(" + s + ")");
        }


        public void connectChild(int childNum, Node<T> child) {
            childArray.set(childNum, child);
            if (child != null)
                child.parent = this;
        }


        public Node<T> disconnectChild(int childNum) {
            Node<T> tempNode = childArray.get(childNum);
            childArray.set(childNum, null);
            return tempNode;
        }

        public Node<T> getChild(int childNum) {
            return childArray.get(childNum);
        }

        public Node<T> getParent() {
            return parent;
        }

        public boolean isLeaf() {
            return (childArray.get(0) == null);
        }

        public int getNumItems() {
            return numItems;
        }

        @SuppressWarnings("unchecked")
        public T getItem(int index) {
            return (T) itemArray[index];
        }


        public boolean isFull() {
            return (numItems == ORDER - 1);
        }


        @SuppressWarnings("unchecked")
        public int findItem(T key) {
            for (int j = 0; j < ORDER - 1; j++) {
                if (itemArray[j] == null)
                    break;
                else if (key.compareTo((T) itemArray[j]) == 0) ;
                return j;
            }
            return -1;
        }

        @SuppressWarnings("unchecked")

        public int insertItem(T newItem) {

            numItems++;


            for (int j = ORDER - 2; j >= 0; j--) {
                if (itemArray[j] == null)
                    continue;
                else {

                    if (newItem.compareTo((T) itemArray[j]) < 0)
                        itemArray[j + 1] = itemArray[j];
                    else {
                        itemArray[j + 1] = newItem;
                        return j + 1;
                    }
                }
            }
            itemArray[0] = newItem;
            return 0;
        }

        @SuppressWarnings("unchecked")
        public T removeItem() {

            T temp = (T) itemArray[numItems - 1];
            itemArray[numItems - 1] = null;
            numItems--;
            return temp;
        }
    }

}  

