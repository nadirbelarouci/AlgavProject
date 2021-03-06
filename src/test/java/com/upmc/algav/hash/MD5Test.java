package com.upmc.algav.hash;

import com.upmc.algav.experiment.MD5;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MD5Test {

    @Test
    public void hash() {
        Map<String, String> hashes = new HashMap<>();
        hashes.put("hello", "5d41402abc4b2a76b9719d911017c592");
        hashes.put("This is amazing", "4ed2db06d6acbc4e33c412a275421253");
        hashes.put("What", "124433700b3275084ede92789a9d83ef");
        hashes.put("Don't do that", "a5abb197ea74c8e913badbe3bbbb9535");
        hashes.put("", "d41d8cd98f00b204e9800998ecf8427e");
        hashes.forEach((s, s2) -> assertEquals(s2, MD5.hash(s)));
    }
}