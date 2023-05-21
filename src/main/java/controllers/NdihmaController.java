package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import service.ConnectionUtil;
import service.Translate;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){
        updateTexts();
    }

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
