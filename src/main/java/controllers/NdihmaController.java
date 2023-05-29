package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import repository.LocaleBundle;
import service.FillimiService;
import service.Translate;

import java.io.IOException;
import java.util.ResourceBundle;


import static service.PasswordUtil.showAlert;


public class NdihmaController extends SceneController {
    Parent root;
    Stage stage;
    Scene scene;
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

    public void barButton(){
        try{
        root = FXMLLoader.load(getClass().getResource("/com/example/fiekorari/barChart.fxml"));
        Stage addDialogStage = new Stage();
        addDialogStage.setTitle("BarChart");
        addDialogStage.initModality(Modality.WINDOW_MODAL);
        addDialogStage.initOwner(stage);
        scene = new Scene(root);
        addDialogStage.setScene(scene);
        System.out.println(FillimiService.getIndeksi);
        addDialogStage.showAndWait();
        }catch(Exception e){
            showAlert("Ka ndodhur nje gabim gjate marrjes se te dhenave");
            e.printStackTrace();
        }
    }
    public void pieButtom(){
        try{
            ResourceBundle bundle = LocaleBundle.bundle(selectedLanguageCode);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/pie.fxml"), bundle);
            root = loader.load();
            PieChartController pieChartController = loader.getController();
            pieChartController.setSelectedLanguageCode(selectedLanguageCode);
//            pieChartController.updateTexts();
//
            Stage addDialogStage = new Stage();
            addDialogStage.setTitle(("PieChart"));
            addDialogStage.initModality(Modality.WINDOW_MODAL);
            addDialogStage.initOwner(stage);
            scene = new Scene(root);
            addDialogStage.setScene(scene);
            System.out.println(FillimiService.getIndeksi);
            addDialogStage.showAndWait();
        }catch(Exception e){
            showAlert("Ka ndodhur nje gabim gjate marrjes se te dhenave");
            e.printStackTrace();
        }
    }
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
