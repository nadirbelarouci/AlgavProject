package com.upmc.algav.experiment;

import com.upmc.algav.key.Key128;
import javafx.application.Application;
import javafx.scene.Scene;
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
import java.util.*;

import static java.util.stream.Collectors.*;

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
                    .collect(toList());

        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private static Duration doExperiment(Set<List<Key128>> allKeys, String heap) {
        return allKeys.stream()
                .map(keys -> ExperimentalStudy.doBuildExperiment(keys, heap))
                .reduce(Duration.ZERO, Duration::plus)
                .dividedBy(allKeys.size());
    }


    public static void main(String... args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Line Chart Sample");
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Size");
        yAxis.setLabel("Running Time (Miliss)");

        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Running time for build heap");

        XYChart.Series<Number, Number> arrayMinHeap = new XYChart.Series<>();
        arrayMinHeap.setName("ArrayMinHeap");
        XYChart.Series<Number, Number> binaryTreeMinHeap = new XYChart.Series<>();
        binaryTreeMinHeap.setName("BinaryTreeMinHeap");
        XYChart.Series<Number, Number> javaAPI = new XYChart.Series<>();
        javaAPI.setName("Java PriorityQueue");

        Map<String, Set<List<Key128>>> fileGroups = Files.list(Paths.get(Main.class.getResource("/cles_alea").toURI()))
                .filter(f -> !f.toFile().isHidden())
                .collect(groupingBy(Main::groupPath, mapping(Main::getKeys, toSet())));


        fileGroups.entrySet()
                .stream()
                .map(e -> new Pair<>(e.getKey(), doExperiment(e.getValue(), "ArrayMinHeap")))
                .forEach(p -> System.out.print(""));


        fileGroups.entrySet()
                .stream()
                .map(e -> new Pair<>(e.getKey(), doExperiment(e.getValue(), "BinaryTreeMinHeap")))
                .forEach(p -> System.out.print(""));

        fileGroups.entrySet()
                .stream()
                .map(e -> new Pair<>(e.getKey(), doExperiment(e.getValue(), "JavaAPI")))
                .forEach(p -> System.out.print(""));

        System.out.println();
        fileGroups.entrySet()
                .stream()
                .map(e -> new Pair<>(Integer.valueOf(e.getKey()), doExperiment(e.getValue(), "ArrayMinHeap")))
//                .sorted(Comparator.comparingInt(p -> Integer.valueOf(p.getKey())))
                .forEach(pair -> arrayMinHeap.getData().add(new XYChart.Data<>(pair.getKey(), pair.getValue().toNanos())));

        fileGroups.entrySet()
                .stream()
                .map(e -> new Pair<>(Integer.valueOf(e.getKey()), doExperiment(e.getValue(), "BinaryTreeMinHeap")))
//                .sorted(Comparator.comparingInt(p -> Integer.valueOf(p.getKey())))
                .forEach(pair -> binaryTreeMinHeap.getData().add(new XYChart.Data<>(pair.getKey(), pair.getValue().toNanos())));

        fileGroups.entrySet()
                .stream()
                .map(e -> new Pair<>(Integer.valueOf(e.getKey()), doExperiment(e.getValue(), "JavaAPI")))
//                .sorted(Comparator.comparingInt(p -> Integer.valueOf(p.getKey())))
                .forEach(pair -> javaAPI.getData().add(new XYChart.Data<>(pair.getKey(), pair.getValue().toNanos())));


        fileGroups.entrySet()
                .stream()
                .map(e -> new Pair<>(e.getKey(), doExperiment(e.getValue(), "ArrayMinHeap")))
                .sorted(Comparator.comparingInt(p -> Integer.valueOf(p.getKey())))
                .forEach(p -> System.out.println(p.getKey() + " = " + p.getValue().toNanos()));


        fileGroups.entrySet()
                .stream()
                .map(e -> new Pair<>(e.getKey(), doExperiment(e.getValue(), "BinaryTreeMinHeap")))
                .sorted(Comparator.comparingInt(p -> Integer.valueOf(p.getKey())))
                .forEach(p -> System.out.println(p.getKey() + " = " + p.getValue().toNanos()));

        fileGroups.entrySet()
                .stream()
                .map(e -> new Pair<>(e.getKey(), doExperiment(e.getValue(), "JavaAPI")))
                .sorted(Comparator.comparingInt(p -> Integer.valueOf(p.getKey())))
                .forEach(p -> System.out.println(p.getKey() + " = " + p.getValue().toNanos()));


        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.X_AXIS);
        lineChart.setAnimated(true);
        lineChart.setCreateSymbols(true);
        lineChart.getData().addAll(arrayMinHeap, binaryTreeMinHeap, javaAPI);

        stage.setScene(scene);

        stage.show();
    }

}
