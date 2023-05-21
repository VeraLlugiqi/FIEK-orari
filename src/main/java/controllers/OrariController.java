package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrariController {
    String sid;
    String timestamp;
    String day;

    public OrariController(String sid, String timestamp, String day) {
        this.sid = sid;
        this.timestamp = timestamp;
        this.day = day;
    }
    public OrariController() {
    }
    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    public void initialize() {
        // Connect to the database

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/knk_orari", "root", "valtrina1*");
             Statement statement = connection.createStatement()) {

            // Execute the query
            ResultSet resultSet = statement.executeQuery("SELECT day, COUNT(*) AS booked_schedules FROM orarizgjedhur GROUP BY day;");

            // Create a list to hold the XYChart.Series objects
            List<XYChart.Series<String, Integer>> seriesList = new ArrayList<>();

            // Create a series for the chart
            XYChart.Series<String, Integer> series = new XYChart.Series<>();

            // Iterate over the result set and add data to the series
            while (resultSet.next()) {
                String day = resultSet.getString("day");
                int count = resultSet.getInt("booked_schedules");
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
