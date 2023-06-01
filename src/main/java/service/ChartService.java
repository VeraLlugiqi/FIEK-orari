package service;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import repository.ChartRepository;

public class ChartService {
    public static void getSallaNumber(String[] sallaArray, int[] numberOfUsage){
        ChartRepository.getSallaNumber(sallaArray, numberOfUsage);
    }

    public static void getDescriptionPie(ObservableList<PieChart.Data> piechartData){
        piechartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " eshte zgjedhur " , data.pieValueProperty() , " here")                        )
        );
    }

    public static void getBarChartInfo(XYChart.Series<String, Integer> series){
        ChartRepository.getBarChartInfo(series);
    }

    }
