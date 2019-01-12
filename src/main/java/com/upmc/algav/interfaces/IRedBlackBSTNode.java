package com.upmc.algav.interfaces;

import java.io.IOException;
import java.io.PrintStream;


public interface IRedBlackBSTNode<T> extends IBinaryTreeNode<T> {
    Color color();

    void color(Color color);

    @Override
    IRedBlackBSTNode<T> left();

    @Override
    IRedBlackBSTNode<T> right();

    @Override
    IRedBlackBSTNode<T> parent();

    default void printTree(PrintStream out) throws IOException {
        if (right() != null) {
            right().printTree(out, true, "");
        }
        printNodeValue(out);
        if (left() != null) {
            left().printTree(out, false, "");
        }
    }

    default void printNodeValue(PrintStream out) throws IOException {
        if (key() == null) {
            out.print("<null>");
        } else {
            out.print(key().toString() + " - " + (color() == Color.RED ? "r" : "b"));
        }
        out.print('\n');
    }

    // use string and not stringbuffer on purpose as we need to change the indent at each recursion
    default void printTree(PrintStream out, boolean isRight, String indent) throws IOException {
        if (right() != null) {
            right().printTree(out, true, indent + (isRight ? "        " : " |      "));
        }
        out.print(indent);
        if (isRight) {
            out.print(" /");
        } else {
            out.print(" \\");
        }
        out.print("----- ");
        printNodeValue(out);
        if (left() != null) {
            left().printTree(out, false, indent + (isRight ? " |      " : "        "));
        }
    }

    enum Color {
        RED, BLACK
    }

}
