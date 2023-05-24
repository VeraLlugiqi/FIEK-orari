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


public class NdihmaController extends SceneController {

    @FXML
    Label fiek_orariLabel;

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
    @FXML
    Label SallatPerdorura;
    @FXML
    Button kthehuButton;
    @FXML
    Label LigjerataShume;
    @FXML
    Button ketusallaButton;
    @FXML
    Button ketuditeButton;

    ActionEvent actionEvent;
    public static String selectedLanguageCode = "sq";
//    @FXML
//    public void initialize(URL url, ResourceBundle resourceBundle){
//
//    }




    public void updateTexts() {

        helpLongTextLabel.setText(Translate.get("helpLongTextLabel.text"));
        helpLabel.setText(Translate.get("helpLabel.text"));
        answer3Label.setText(Translate.get("answer3Label.text"));
        question3Label.setText(Translate.get("question3Label.text"));
        answer2Label.setText(Translate.get("answer2Label.text"));
        question2Label.setText(Translate.get("question2Label.text"));
        answer1Label.setText(Translate.get("answer1Label.text"));
        question1Label.setText(Translate.get("question1Label.text"));
        fiek_orariLabel.setText(Translate.get("fiek_orariLabel.text"));
        SallatPerdorura.setText(Translate.get("SallatPerdorura.text"));
        ketusallaButton.setText(Translate.get("ketusallaButton.text"));
        LigjerataShume.setText(Translate.get("LigjerataShume.text"));
        kthehuButton.setText(Translate.get("kthehuButton.text"));
        ketuditeButton.setText(Translate.get("ketuditeButton.text"));
    }
    public void setSelectedLanguageCode(String languageCode) {
        selectedLanguageCode = languageCode;
    }

    public void switchToFillimi() throws IOException{
        switchToFillimi(actionEvent);
    }
}
