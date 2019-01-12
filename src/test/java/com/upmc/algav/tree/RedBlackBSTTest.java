package com.upmc.algav.tree;

import com.upmc.algav.interfaces.IRedBlackBST;
import com.upmc.algav.interfaces.IKey128;
import com.upmc.algav.experiment.Key128;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class RedBlackBSTTest {
    private IRedBlackBST<IKey128> tree = new RedBlackBST<>();

    @Before

    public void setUp() throws Exception {
        Stream.of(32, 10, 40, 25, 1, 20, 55, 88, -2, 2, -2, 66, 60, 1, 4)
                .map(String::valueOf)
                .map(Key128::new)
                .forEach(tree::insert);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void search() {
        assertNull(tree.search(new Key128("-555")));
        assertNull(tree.search(new Key128("-3")));
        assertNull( tree.search(new Key128("200")));
        assertNull( tree.search(new Key128("555")));
        assertNotNull(tree.search(new Key128("1")));
        assertNotNull(tree.search(new Key128("88")));
    }

    @Test
    public void insert() throws Exception{
        tree.insert(new Key128("30"));
        tree.insert(new Key128("15"));
        tree.insert(new Key128("-8"));
        List<Key128> list = Stream.of(-8, -2, 1, 2, 4, 10,15, 20, 25, 30,32, 40, 55, 60, 66, 88).map(String::valueOf).map(Key128::new).collect(Collectors.toList());
        assertEquals(list, tree.explode());
        tree.root().printTree(System.out);
    }



}
