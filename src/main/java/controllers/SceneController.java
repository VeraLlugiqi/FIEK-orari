package controllers;


import java.io.IOException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.LocaleBundle;
import service.Translate;

abstract class SceneController {
    public static String selectedLanguageCode = "sq";

    private Stage stage;
    private Scene scene;
    private  Parent root;

    @FXML
    public void switchToFillimi(ActionEvent event) throws IOException {
        ResourceBundle bundle = LocaleBundle.bundle(selectedLanguageCode);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/fillimi.fxml"), bundle);
        root = loader.load();
        FillimiController fillimiController = loader.getController();
        fillimiController.setSelectedLanguageCode(selectedLanguageCode);
        fillimiController.updateTexts();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle(Translate.get("fillimi.text"));
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToMenaxhoOret(ActionEvent event) throws IOException {
        ResourceBundle bundle = LocaleBundle.bundle(selectedLanguageCode);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/menaxhoOret.fxml"), bundle);
        root = loader.load();
        MenaxhoOretController menaxhoOretController = loader.getController();
        menaxhoOretController.setSelectedLanguageCode(selectedLanguageCode);
        menaxhoOretController.updateTexts();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle(Translate.get("menaxhoOret.text"));
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToProfili(ActionEvent event) throws IOException {
        ResourceBundle bundle = LocaleBundle.bundle(selectedLanguageCode);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/profili.fxml"), bundle);
        root = loader.load();
        PasswordUpdateController passwordUpdateController = loader.getController();
        passwordUpdateController.setSelectedLanguageCode(selectedLanguageCode);
        passwordUpdateController.updateTexts();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle(Translate.get("profili.text"));
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToLogin(ActionEvent event) throws IOException {
        // Load the login screen FXML using the selected language bundle
        ResourceBundle bundle = LocaleBundle.bundle(selectedLanguageCode);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/login.fxml"), bundle);
        root = loader.load();
        LoginController loginController = loader.getController();
        loginController.setSelectedLanguageCode(selectedLanguageCode);
        loginController.updateTexts();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(Translate.get("login.text"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void switchToNdihma(ActionEvent event) throws IOException {
        ResourceBundle bundle = LocaleBundle.bundle(selectedLanguageCode);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/ndihma.fxml"), bundle);
        root = loader.load();
        NdihmaController ndihmaController = loader.getController();
        ndihmaController.setSelectedLanguageCode(selectedLanguageCode);
        ndihmaController.updateTexts();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle(Translate.get("ndihma.text"));
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void switchToOrari(ActionEvent event) throws IOException {
        ResourceBundle bundle = LocaleBundle.bundle(selectedLanguageCode);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/orari.fxml"), bundle);
        root = loader.load();
        OrariFinalController orariController = loader.getController();
        orariController.setSelectedLanguageCode(selectedLanguageCode);
        orariController.updateTexts(); // Call updateTexts() in the SignupController
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle(Translate.get("orari.text"));
        stage.setScene(scene);
        stage.show();
    }

    }
