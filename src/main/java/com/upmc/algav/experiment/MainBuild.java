package com.upmc.algav.experiment;

import com.upmc.algav.key.IKey128;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.Duration;
import java.util.List;

public class MainBuild extends Application {
    private BorderPane borderPane;

    private static Duration build(List<List<IKey128>> allKeys, String heap) {
        return allKeys.stream()
                .map(keys -> ExperimentalStudy.doExperiment("build", keys, heap))
                .reduce(Duration.ZERO, Duration::plus)
                .dividedBy(allKeys.size());
    }


    public static void main(String... args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {


        borderPane = new BorderPane();


        VBox leftPane = new VBox();
        leftPane.setPadding(new Insets(20));


        stage.setTitle("Line Chart Sample");

        Scene scene = new Scene(borderPane, 800, 600);

        plotRandomKeyBuildLines();
        stage.setScene(scene);

        stage.show();
    }

    private Series<Number, Number> addRandomKeysLine(String heap) {
        Series<Number, Number> series = new Series<>();
        series.setName(heap);
        RandomKeysReader.getData()
                .forEach((k, v) -> series.getData().add(new Data<>(k, build(v, heap).toNanos())));
        return series;

    }


    @SuppressWarnings("unchecked")
    private Node plotRandomKeyBuildLines() {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Size");
        yAxis.setLabel("Running Time (Nanos)");

        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Running time for build heap");


        lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.X_AXIS);

        addRandomKeysLine("ArrayMinHeap");
        addRandomKeysLine("BinaryTreeMinHeap");
        addRandomKeysLine("BinomialMinHeap");

        lineChart.getData().addAll(addRandomKeysLine("ArrayMinHeap"),
                addRandomKeysLine("BinaryTreeMinHeap"),
                addRandomKeysLine("BinomialMinHeap"));
        borderPane.setCenter(lineChart);
        return lineChart;
    }




    // TODO create bar chart for insert / delete min / union random keys
    // TODO create BST hash from Shakespeare and add each word into a LinkedHashSet
    // TODO find duplicates
    // TODO create a heap
    // TODO create bar chart for LinkedHashSet hashes for all operations insert / delete min /union

    // TODO for union of Shakespeare split LinkedHsahSet in two , build heap then do the union

}
