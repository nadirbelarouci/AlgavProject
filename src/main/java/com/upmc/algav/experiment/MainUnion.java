package com.upmc.algav.experiment;

import com.upmc.algav.heap.MinHeap;
import com.upmc.algav.key.IKey128;
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
import java.util.List;

import static com.upmc.algav.experiment.ExperimentalStudy.doExperiment;

public class MainUnion extends Application {

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
        plotUnionChartBar();
        stage.setScene(scene);

        stage.show();
    }


    void plotUnionChartBar() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc =
                new BarChart<>(xAxis, yAxis);
        bc.setTitle("Running time for union of 2 heaps of size 5000");
        xAxis.setLabel("size");
        yAxis.setLabel("Running Time (Nanos)");
        Series<String, Number> size5000 = new Series<>();
        size5000.setName("5000");
        Series<String, Number> size10000 = new Series<>();
        size10000.setName("10000");

        List<IKey128> keys = (List<IKey128>) RandomKeysReader.getHeaps().get("ArrayMinHeap").get(10000).elements();

        RandomKeysReader.getHeaps().forEach((s, map) -> {
            MinHeap one, two = map.get(5000);
            one = two;

            doExperiment("union", one, two);
            doExperiment("build", keys, s);
            size5000.getData().add(new Data<>(s, doExperiment("union", one, two).toNanos()));
            size10000.getData().add(new Data<>(s, doExperiment("build", keys, s).toNanos()));
        });


        bc.getData().addAll(size5000, size10000);

        borderPane.setCenter(bc);
    }


    // TODO create bar chart for insert / delete min / union random keys
    // TODO create BST hash from Shakespeare and add each word into a LinkedHashSet
    // TODO find duplicates
    // TODO create a heap
    // TODO create bar chart for LinkedHashSet hashes for all operations insert / delete min /union

    // TODO for union of Shakespeare split LinkedHsahSet in two , build heap then do the union

}
