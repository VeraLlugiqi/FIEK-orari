package controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import service.ConnectionUtil;
import service.Translate;

import java.net.PortUnreachableException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import static service.PasswordUtil.showAlert;

public class PieChartController implements Initializable {
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
                                data.getName(), " eshte zgjedhur " , data.pieValueProperty() , " here")                        )
                );
        pieChart.getData().addAll(piechartData);
//        updateTexts();
    }


    public void setSelectedLanguageCode(String languageCode) {
        selectedLanguageCode = languageCode;
    }

   // public void updateTexts(){
//        sallatMeTePerdorura.setText(Translate.get("sallatMeTePerdorura.text"));
//    }


}

