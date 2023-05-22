package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.PortUnreachableException;
import java.net.URL;
import java.util.ResourceBundle;

public class PieChartController implements Initializable {
    @FXML
    private PieChart pieChart;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        ObservableList<PieChart.Data>piechartData = FXCollections.observableArrayList(
                new javafx.scene.chart.PieChart.Data("Zana", 10),
                new javafx.scene.chart.PieChart.Data("Ereza", 5)
        );
        pieChart.getData().addAll(piechartData);
    }
}
