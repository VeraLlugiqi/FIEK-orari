package controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import service.ConnectionUtil;

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
        // Connect to the database
        try {
            conn = ConnectionUtil.getConnection();
            ps = conn.prepareStatement("SELECT day, COUNT(*) AS booked_schedules FROM orarizgjedhur WHERE availableOrariZgjedhur = 1 GROUP BY day ");
            // Execute the query
             rs = ps.executeQuery();

            // Create a list to hold the XYChart.Series objects
            List<XYChart.Series<String, Integer>> seriesList = new ArrayList<>();

            // Create a series for the chart
            XYChart.Series<String, Integer> series = new XYChart.Series<>();

            // Iterate over the result set and add data to the series
            while (rs.next()) {
                String day = rs.getString("day");
                int count = rs.getInt("booked_schedules");
                series.getData().add(new XYChart.Data<>(day, count));
            }

            // Add the series to the list
            seriesList.add(series);

            // Set the data to the bar chart
            barChart.getData().addAll(seriesList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
