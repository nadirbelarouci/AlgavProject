package com.upmc.algav;

import com.upmc.algav.experiment.Key128;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class Key128Test {


    private static List<Key128> getKeys(String s) {
        try {
            return Files.lines(Paths.get(s)).map(line -> line.substring(2)).map(Key128::new).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Test
    public void less() {
        Key128 first = new Key128("10");
        Key128 second = new Key128("5");
        assertFalse(first.less(second));
        assertTrue(second.less(first));
    }

    @Test
    public void eq() {
        Key128 first = new Key128("10");
        Key128 second = new Key128("10");
        assertTrue(first.eq(first));
        assertTrue(first.eq(second));
        assertTrue(second.eq(first));
    }


}