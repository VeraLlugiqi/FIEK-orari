package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import service.ConnectionUtil;
import service.Translate;

import java.net.URL;
import java.util.ResourceBundle;


public class OrariController {

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
    Label longTextLabel;
    @FXML
    Label scheduleLabel;

    public static String selectedLanguageCode = "sq";
    String sid;
    String timestamp;
    String day;

    public OrariController(String sid, String timestamp, String day) {
        this.sid = sid;
        this.timestamp = timestamp;
        this.day = day;
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


    public void initialize(URL url, ResourceBundle resourceBundle){
        updateTexts();
    }
    public void setSelectedLanguageCode(String languageCode) {
        selectedLanguageCode = languageCode;
    }

    public void updateTexts(){
        longTextLabel.setText(Translate.get("longTextLabel.text"));
        scheduleLabel.setText(Translate.get("scheduleLabel.text"));
        logoutButton.setText(Translate.get("logoutButton.text"));
        NdihmaButton.setText(Translate.get("NdihmaButton.text"));
        Oraributton.setText(Translate.get("Oraributton.text"));
        profileButton.setText(Translate.get("profileButton.text"));
        manageClassButton.setText(Translate.get("manageClassButton.text"));
        startButton.setText(Translate.get("startButton.text"));
        fiek_orariLabel.setText(Translate.get("fiek_orariLabel.text"));

    }
}
