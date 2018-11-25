package com.upmc.algav.heap;

import com.upmc.algav.key.Key128;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public abstract class MinHeapTest {
    protected List<Key128> keys;
    protected MinHeap<Key128> heap;
    protected List<Key128> sortedKeys;

    public MinHeapTest(Path path) throws IOException {
        super();
        keys = Files.lines(path)
                .map(s -> s.startsWith("0x") ? s.substring(2) : s)
                .map(Key128::new)
                .collect(Collectors.toList());
        sortedKeys = new ArrayList<>(keys);
        sortedKeys.sort(Comparator.naturalOrder());
    }

    @Parameters(name = "{0}")
    public static Collection<Path> getPath() throws Exception {
        return Files.list(Paths.get(ArrayMinHeapTest.class.getResource("/cles_alea").toURI()))
                .filter(f -> !f.toFile().isHidden())
                .map(f -> f.normalize())
                .collect(Collectors.toList());
    }

    @Test
    public void deleteMin() {
        sortedKeys.forEach(key -> assertEquals(key, heap.deleteMin()));
        assertTrue(heap.empty());
        assertNull(heap.deleteMin());
    }
}
