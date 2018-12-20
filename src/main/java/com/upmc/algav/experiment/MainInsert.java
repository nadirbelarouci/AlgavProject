package com.upmc.algav.experiment;

import com.upmc.algav.heap.MinHeap;
import com.upmc.algav.key.IKey128;
import javafx.application.Application;
import javafx.geometry.Insets;
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
import java.util.Map;

import static com.upmc.algav.experiment.ExperimentalStudy.doExperiment;

public class MainInsert extends Application {

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

        plotExperiments("insert");
        stage.setScene(scene);

        stage.show();
    }


    private Series<Number, Number> addExprLine(String exp, String heap) {

        Series<Number, Number> series = new Series<>();
        series.setName(heap);
        Map<Integer, MinHeap> heaps = RandomKeysReader.getHeaps().get(heap);
        heaps.forEach((i, minHeap) -> {
            Object args[] = null;
            switch (exp) {

                case "insert":
                    args = new Object[2];
                    args[0] = minHeap;
                    args[1] = RandomKeysReader.getData().get(100).get(0).get(0);
                    break;
                default:
                    return;
            }
            series.getData()
                    .add(new Data<>(i, doExperiment(exp, args).toNanos()));
        });

        return series;
    }


    private void plotExperiments(String experiment) {

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<Number, Number> insertLineChart =
                new LineChart<>(xAxis, yAxis);
        insertLineChart.setTitle("Running time for " + experiment);
        xAxis.setLabel("size");
        yAxis.setLabel("Running Time (Nanos)");

        // he
        addExprLine(experiment, "ArrayMinHeap");
        addExprLine(experiment, "BinaryTreeMinHeap");
        addExprLine(experiment, "BinomialMinHeap");

        insertLineChart.getData().addAll(addExprLine(experiment, "ArrayMinHeap"),
                addExprLine(experiment, "BinaryTreeMinHeap"),
                addExprLine(experiment, "BinomialMinHeap"));
        insertLineChart.setAxisSortingPolicy(LineChart.SortingPolicy.X_AXIS);

        borderPane.setCenter(insertLineChart);
    }


    // TODO create bar chart for insert / delete min / union random keys
    // TODO create BST hash from Shakespeare and add each word into a LinkedHashSet
    // TODO find duplicates
    // TODO create a heap
    // TODO create bar chart for LinkedHashSet hashes for all operations insert / delete min /union

    // TODO for union of Shakespeare split LinkedHsahSet in two , build heap then do the union

}
