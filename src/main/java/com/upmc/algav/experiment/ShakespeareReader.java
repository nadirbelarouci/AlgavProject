package com.upmc.algav.experiment;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ShakespeareReader {
    private static Stream<String> words;

    static {

        try {
            words = Files.list(Paths.get(ShakespeareReader.class.getResource("/Shakespeare").toURI()))
                    .filter(f -> !f.toFile().isHidden())
                    .flatMap(ShakespeareReader::getWords);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }


    }

    private static Stream<String> getWords(Path path) {
        try {
            return Files.lines(path);

        } catch (IOException e) {
            return Stream.empty();
        }
    }

    public static Stream<String> getData() {
        return words;
    }
}
