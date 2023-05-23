package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import service.ConnectionUtil;
import service.Translate;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import static service.PasswordUtil.showAlert;

class pieChartController implements Initializable {
    @FXML
    private PieChart pieChart;
    @FXML
    Label sallatMeTePerdorura;
    String[] sallaArray = new String[3];
    int[] numberOfUsage = new int[3];
    public static String selectedLanguageCode = "sq";


    public void getSallaNumber() {
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT salla, COUNT(*) AS count " +
                    "FROM orarizgjedhur WHERE availableOrariZgjedhur = 1 " +
                    "GROUP BY salla " +
                    "ORDER BY count DESC " +
                    "LIMIT 3");
            ResultSet rs = ps.executeQuery();
            int index = 0;
            while (rs.next()) {
                String salla = rs.getString("salla");
                int count = rs.getInt("count");
                sallaArray[index] = salla;
                numberOfUsage[index] = count;
                index++;
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            showAlert(Translate.get("gabimMarrjaDhenave.text"));

        }

    }
    @FXML
    public BarChart<String, Integer> barChart;

    @FXML
    public CategoryAxis xAxis;
    @FXML
    public NumberAxis yAxis;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getSallaNumber();
        ObservableList<PieChart.Data> piechartData = FXCollections.observableArrayList(
                new javafx.scene.chart.PieChart.Data(sallaArray[0], numberOfUsage[0]),
                new javafx.scene.chart.PieChart.Data(sallaArray[1], numberOfUsage[1]),
                new javafx.scene.chart.PieChart.Data(sallaArray[2], numberOfUsage[2])
        );
        piechartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), Translate.get("eshteZgjedhur.text") , data.pieValueProperty() , Translate.get("hereZgjedhur.text =")                        )
                ));
        pieChart.getData().addAll(piechartData);

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
        updateTexts();

    }


    public void setSelectedLanguageCode(String languageCode) {
        selectedLanguageCode = languageCode;
    }

    public void updateTexts(){
        sallatMeTePerdorura.setText(Translate.get("sallatMeTePerdorura.text"));
    }


}



public class NdihmaController extends SceneController {

    @FXML
    Label fiek_orariLabel;
    @FXML
    Button startButton;
    @FXML
    Button manageClassButton;
    @FXML
    Button profileButton;
    @FXML
    Button Oraributton;
    @FXML
    Button NdihmaButton;
    @FXML
    Button logoutButton;

    @FXML
    Label helpLongTextLabel;
    @FXML
    Label helpLabel;
    @FXML
    Label answer3Label;
    @FXML
    Label question3Label;
    @FXML
    Label answer2Label;
    @FXML
    Label question2Label;
    @FXML
    Label answer1Label;
    @FXML
    Label question1Label;

    ActionEvent actionEvent;
    public static String selectedLanguageCode = "sq";
//    @FXML
//    public void initialize(URL url, ResourceBundle resourceBundle){
//
//    }

    public void switchToFillimi() throws IOException {
        switchToFillimi(actionEvent);
    }
    public void switchToMenaxhoOret() throws IOException{
        switchToMenaxhoOret(actionEvent);
    }
    public void switchToProfili() throws IOException{
        switchToProfili(actionEvent);
    }
    public void switchToLogin() throws IOException{
        switchToLogin(actionEvent);
    }
    public void switchToNdihma() throws IOException{
        switchToNdihma(actionEvent);
    }
    public void switchToOrari() throws IOException{
        switchToOrari(actionEvent);
    }



    public void updateTexts() {

        helpLongTextLabel.setText(Translate.get("helpLongTextLabel.text"));
        helpLabel.setText(Translate.get("helpLabel.text"));
        answer3Label.setText(Translate.get("answer3Label.text"));
        question3Label.setText(Translate.get("question3Label.text"));
        answer2Label.setText(Translate.get("answer2Label.text"));
        question2Label.setText(Translate.get("question2Label.text"));
        answer1Label.setText(Translate.get("answer1Label.text"));
        question1Label.setText(Translate.get("question1Label.text"));
        logoutButton.setText(Translate.get("logoutButton.text"));
        NdihmaButton.setText(Translate.get("NdihmaButton.text"));
        Oraributton.setText(Translate.get("Oraributton.text"));
        profileButton.setText(Translate.get("profileButton.text"));
        manageClassButton.setText(Translate.get("manageClassButton.text"));
        startButton.setText(Translate.get("startButton.text"));
        fiek_orariLabel.setText(Translate.get("fiek_orariLabel.text"));

    }
    public void setSelectedLanguageCode(String languageCode) {
        selectedLanguageCode = languageCode;
    }


}
