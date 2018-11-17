package com.upmc.algav;

import com.upmc.algav.key.Key128;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//@RunWith(Parameterized.class)

public class Key128Test {


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