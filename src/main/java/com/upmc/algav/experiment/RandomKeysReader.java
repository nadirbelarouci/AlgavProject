package com.upmc.algav.experiment;

import com.upmc.algav.heap.ArrayMinHeap;
import com.upmc.algav.heap.MinHeap;
import com.upmc.algav.key.IKey128;
import com.upmc.algav.key.Key128;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public class RandomKeysReader  {

    private static Map<Integer, List<List<IKey128>>> keyGroups;
    private static Map<String, Map<Integer, MinHeap>> heaps = new HashMap<>();

    static {

        try {
            keyGroups = Files.list(Paths.get(RandomKeysReader.class.getResource("/cles_alea").toURI()))
                    .filter(f -> !f.toFile().isHidden())
                    .collect(groupingBy(RandomKeysReader::groupPath, mapping(RandomKeysReader::getKeys, toList())));

            Map<Integer, MinHeap> arrayHeaps = new HashMap<>();
            keyGroups.keySet().forEach(i -> arrayHeaps.put(i, new ArrayMinHeap(keyGroups.get(i).get(0))));
            heaps.put("ArrayMinHeap", arrayHeaps);
            Map<Integer, MinHeap> binaryHeaps = new HashMap<>();
            keyGroups.keySet().forEach(i -> binaryHeaps.put(i, new ArrayMinHeap(keyGroups.get(i).get(0))));
            heaps.put("BinaryTreeMinHeap", binaryHeaps);
            Map<Integer, MinHeap> binomialHeaps = new HashMap<>();
            keyGroups.keySet().forEach(i -> binomialHeaps.put(i, new ArrayMinHeap(keyGroups.get(i).get(0))));
            heaps.put("BinomialMinHeap", binomialHeaps);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }


    }

    private static Integer groupPath(Path path) {
        String s = path.toString();
        s = s.substring(s.lastIndexOf("_") + 1, s.lastIndexOf("."));
        return Integer.valueOf(s);
    }

    private static List<IKey128> getKeys(Path path) {
        try {
            return Files.lines(path)
                    .map(Object::toString)
                    .filter(key -> key.startsWith("0x"))
                    .map(key -> key.substring(2))
                    .map(Key128::new)
                    .collect(toList());

        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public static Map<Integer, List<List<IKey128>>> getData() {
        return keyGroups;
    }

    public static Map<String, Map<Integer, MinHeap>> getHeaps() {
        return heaps;
    }
}
