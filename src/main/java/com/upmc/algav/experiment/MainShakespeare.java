package com.upmc.algav.experiment;

import com.upmc.algav.hash.MD5;
import com.upmc.algav.heap.ArrayMinHeap;
import com.upmc.algav.heap.BinomialMinHeap;
import com.upmc.algav.heap.MinHeap;
import com.upmc.algav.key.IKey128;
import com.upmc.algav.key.Key128;
import com.upmc.algav.tree.RedBlackBST;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.Duration;
import java.util.*;

import static com.upmc.algav.experiment.ExperimentalStudy.doExperiment;

public class MainShakespeare extends Application {
    private BorderPane borderPane;

    private static Duration build(List<List<IKey128>> allKeys, String heap) {
        return allKeys.stream()
                .map(keys -> doExperiment("build", keys, heap))
                .reduce(Duration.ZERO, Duration::plus)
                .dividedBy(allKeys.size());
    }


    public static void main(String... args) throws Exception {
        launch(args);

    }

    static String add(LinkedHashSet<String> set, String word) {
        set.add(word);
        return word;
    }

    //    @Override
    public void start(Stage stage) throws Exception {
        LinkedHashSet<String> words = new LinkedHashSet<>();
        RedBlackBST<IKey128> tree = new RedBlackBST<>();
        ShakespeareReader.getData()
                .parallel()
                .map(word -> add(words, word))
                .map(MD5::hash)
                .map(Key128::new)
                .forEach(tree::insert);

        System.out.println("Tree size: " + tree.size());
        System.out.println("finding duplicates ...");
        System.out.println("unique words count: " + words.size());
        Map<IKey128, Vector<String>> duplicates = new Hashtable<>();
        words.parallelStream().forEach(word -> {
            IKey128 key = new Key128(MD5.hash(word));
            if (tree.search(key) != null) {
                if (duplicates.containsKey(key)) {
                    duplicates.get(key).add(word);
                } else {
                    Vector<String> vector = new Vector<>();
                    vector.add(word);
                    duplicates.put(key, vector);
                }
            }
        });


        System.out.println("Duplicates are: " );
        duplicates.entrySet().parallelStream().forEach(e -> {
            if (e.getValue().size() > 1)
                System.out.println(e.getKey() + " ->" + e.getValue());
        });


        borderPane = new BorderPane();


        VBox leftPane = new VBox();
        leftPane.setPadding(new Insets(20));

        stage.setTitle("Line Chart Sample");

        plotUnionChartBar(new ArrayList<>(duplicates.keySet()));
        Scene scene = new Scene(borderPane, 800, 600);
        stage.setScene(scene);

        stage.show();
    }

    void plotUnionChartBar(List<IKey128> keys) {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc =
                new BarChart<>(xAxis, yAxis);
        bc.setTitle("Comparaison between ArrayMinHeap and BinomialHeap");
        xAxis.setLabel("operations");
        yAxis.setLabel("Running Time (Nanos)");
        Series<String, Number> arrayMinHeap = new Series<>();
        arrayMinHeap.setName("ArrayMinHeap");
        Series<String, Number> binomialMinHeap = new Series<>();
        binomialMinHeap.setName("BinomialMinHeap");

        doExperiment("build", keys, "ArrayMinHeap");
        arrayMinHeap.getData().add(new Data<>("build",
                doExperiment("build", keys, "ArrayMinHeap").toNanos()));
        doExperiment("build", keys, "BinomialMinHeap");
        binomialMinHeap.getData().add(new Data<>("build",
                doExperiment("build", keys, "BinomialMinHeap").toNanos()));

        // insert
        MinHeap heap1 = new ArrayMinHeap(keys);
        Key128 key128 = new Key128(MD5.hash("Welcome"));
        doExperiment("insert", heap1, key128);
        arrayMinHeap.getData().add(new Data<>("insert",
                doExperiment("insert", heap1, key128).toNanos()));
        MinHeap heap2 = new BinomialMinHeap(keys);
        doExperiment("insert", heap2, key128);
        binomialMinHeap.getData().add(new Data<>("insert",
                doExperiment("insert", heap2, key128).toNanos()));

        // delete
        doExperiment("deleteMin", heap1);
        arrayMinHeap.getData().add(new Data<>("deleteMin",
                doExperiment("deleteMin", heap1).toNanos()));
        doExperiment("deleteMin", heap2);
        binomialMinHeap.getData().add(new Data<>("deleteMin",
                doExperiment("deleteMin", heap2).toNanos()));

        // union
        List<IKey128> k1 = keys.subList(0, keys.size() / 2);
        List<IKey128> k2 = keys.subList(keys.size() / 2, keys.size() - 1);
        MinHeap aone = new ArrayMinHeap(k1);
        MinHeap atwo = new ArrayMinHeap(k2);
        doExperiment("union", aone, atwo);
        arrayMinHeap.getData().add(new Data<>("union",
                doExperiment("union", aone, atwo).toNanos()));

        MinHeap bone = new BinomialMinHeap(k1);
        MinHeap btwo = new BinomialMinHeap(k2);
        doExperiment("union", bone, btwo);
        binomialMinHeap.getData().add(new Data<>("union",
                doExperiment("union", bone, btwo).toNanos()));


        bc.getData().addAll(arrayMinHeap, binomialMinHeap);

        borderPane.setCenter(bc);
    }

}
