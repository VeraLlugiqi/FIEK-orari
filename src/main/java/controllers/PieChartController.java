package controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import service.ChartService;

import java.net.URL;
import java.util.ResourceBundle;

public class PieChartController implements Initializable {
    @FXML
    private PieChart pieChart;
    @FXML
    Label sallatMeTePerdorura;
    String[] sallaArray = new String[3];
    int[] numberOfUsage = new int[3];
    public static String selectedLanguageCode = "sq";



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ChartService.getSallaNumber(sallaArray, numberOfUsage);
        ObservableList<PieChart.Data> piechartData = FXCollections.observableArrayList(
                new javafx.scene.chart.PieChart.Data(sallaArray[0], numberOfUsage[0]),
                new javafx.scene.chart.PieChart.Data(sallaArray[1], numberOfUsage[1]),
                new javafx.scene.chart.PieChart.Data(sallaArray[2], numberOfUsage[2])
        );
        ChartService.getDescriptionPie(piechartData);
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

