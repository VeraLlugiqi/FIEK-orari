package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import service.NdihmaService;
import service.Translate;

import java.io.IOException;


public class NdihmaController extends SceneController {
    Parent root;
    Stage stage;
    Scene scene;

    ActionEvent actionEvent;
    public static String selectedLanguageCode = "sq";
    NdihmaService ndihmaService = new NdihmaService();
    @FXML
    public void barButton(){
        ndihmaService.barButton(root, scene, stage);
    }
    @FXML
    public void pieButton(){
        ndihmaService.pieButton(root, scene, stage);
    }

//    @FXML
//    public void initialize(URL url, ResourceBundle resourceBundle){
//
//    }

//--------------------Gjuha-------------------------------------
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
