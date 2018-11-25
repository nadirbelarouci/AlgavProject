package com.upmc.algav.experiment;

import com.upmc.algav.key.Key128;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main extends Application {

    private static String groupPath(Path path) {
        String s = path.toString();
        return s.substring(s.lastIndexOf("_") + 1, s.lastIndexOf("."));
    }

    private static List<Key128> getKeys(Path path) {
        try {
            return Files.lines(path)
                    .map(Object::toString)
                    .filter(key -> key.startsWith("0x"))
                    .map(key -> key.substring(2))
                    .map(Key128::new)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private static Duration doExperiment(List<Path> paths, String heap) {
        return paths.stream()
                .map(Main::getKeys)
                .map(keys -> ExperimentalStudy.doBuildExperiment(keys, heap))
                .reduce(Duration.ZERO, Duration::plus)
                .dividedBy(paths.size());
    }

    public static void main(String... args) throws Exception {


        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Line Chart Sample");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Size");
        yAxis.setLabel("Running Time (nano)");

        final LineChart<String, Number> lineChart =
                new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Running time for build heap");

        XYChart.Series<String, Number> arrayMinHeap = new XYChart.Series<>();
        arrayMinHeap.setName("ArrayMinHeap");
        XYChart.Series<String, Number> binaryTreeMinHeap = new XYChart.Series<>();
        binaryTreeMinHeap.setName("BinaryTreeMinHeap");
        XYChart.Series<String, Number> javaAPI = new XYChart.Series<>();
        javaAPI.setName("BinomialMinHeap");
        Map<String, List<Path>> fileGroups = Files.list(Paths.get(Main.class.getResource("/cles_alea").toURI()))
                .filter(f -> !f.toFile().isHidden())
                .collect(Collectors.groupingBy(Main::groupPath));

        fileGroups.entrySet()
                .stream()
                .map(e -> new Pair<>(e.getKey(), doExperiment(e.getValue(), "ArrayMinHeap")))
                .sorted(Comparator.comparingInt(p -> Integer.valueOf(p.getKey())))
                .forEach(pair -> arrayMinHeap.getData().add(new XYChart.Data<>(pair.getKey(), pair.getValue().toNanos())));

        fileGroups.entrySet()
                .stream()
                .map(e -> new Pair<>(e.getKey(), doExperiment(e.getValue(), "BinaryTreeMinHeap")))
                .sorted(Comparator.comparingInt(p -> Integer.valueOf(p.getKey())))
                .forEach(pair -> binaryTreeMinHeap.getData().add(new XYChart.Data<>(pair.getKey(), pair.getValue().toNanos())));

        fileGroups.entrySet()
                .stream()
                .map(e -> new Pair<>(e.getKey(), doExperiment(e.getValue(), "BinomialMinHeap")))
                .sorted(Comparator.comparingInt(p -> Integer.valueOf(p.getKey())))
                .forEach(pair -> javaAPI.getData().add(new XYChart.Data<>(pair.getKey(), pair.getValue().toNanos())));


        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().addAll(arrayMinHeap, binaryTreeMinHeap, javaAPI);

        stage.setScene(scene);
        stage.show();
    }

}
