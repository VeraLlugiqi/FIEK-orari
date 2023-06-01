package controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import service.ChartService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarChartController {

    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    public void initialize() {
        List<XYChart.Series<String, Integer>> seriesList = new ArrayList<>();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        ChartService.getBarChartInfo(series);
        seriesList.add(series);
        barChart.getData().addAll(seriesList);
    }
}
