
package controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import service.Translate;

import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.LocaleBundle;
import models.User;
import service.ConnectionUtil;
import service.PasswordUtil;

public class LoginController {
    private UserController userController;
    private ResourceBundle resources;

    public LoginController() {
        userController = new UserController();
    }

    @FXML
    private TextField idTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label fiek_orariLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Button signUpButton;

    @FXML
    private Label notRegisteredLabel;
    private Stage stage;
    private Scene scene;
    private Parent root;

   public static String selectedLanguageCode = "sq"; // Default language code
//
//    public void initialize() {
//        selectedLanguageCode = "en";
//    }



   public void updateTexts() {
        fiek_orariLabel.setText(Translate.get("fiek_orariLabel.text"));
        idTextField.setPromptText(Translate.get("idTextField.promptText"));
        passwordField.setPromptText(Translate.get("passwordField.promptText"));
        loginButton.setText(Translate.get("loginButton.text"));
        notRegisteredLabel.setText(Translate.get("notRegisteredLabel.text"));
        signUpButton.setText(Translate.get("signUpButton.text"));
    }
    public void loginUser(ActionEvent event) {
        performLogin();
    }

    public void loginUserWithEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            performLogin();
        }
    }

    private void performLogin() {
        String idNumber = idTextField.getText();
        String password = passwordField.getText();
        ResourceBundle bundle = LocaleBundle.bundle(selectedLanguageCode);

        // Validate if any field is empty
        if (idNumber.isEmpty() || password.isEmpty()) {
            showErrorAlert(Translate.get("login.error.emptyFields"));
            return;
        }

        // Retrieve the user from the database
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM user WHERE idNumber = ?")) {
            statement.setString(1, idNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                byte[] salt = hexStringToByteArray(resultSet.getString("salt"));
                String hashedPassword = PasswordUtil.hashPassword(password, salt);

                if (storedPassword.equals(hashedPassword)) {
                    try {

                        UserController.loggedInUserId = idNumber;
                        System.out.println(UserController.loggedInUserId);

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/fillimi.fxml"));
                        root = loader.load();
                        FillimiController fillimiController = loader.getController();
                        fillimiController.setSelectedLanguageCode(selectedLanguageCode);

                        fillimiController.updateTexts(); // Call updateTexts() in the SignupController
                        Stage stage = (Stage) idTextField.getScene().getWindow();
//                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setTitle(Translate.get("signUpButton.text"));
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        showErrorAlert(Translate.get("login.error.loadScreen"));
                        e.printStackTrace();
                    }
                } else {
                    showErrorAlert(Translate.get("login.error.invalidCredentials"));
                }

            } else {
                showErrorAlert(Translate.get("login.error.invalidCredentials"));
            }
        } catch (SQLException e) {
            showErrorAlert(Translate.get("login.error.loginFailed"));
            e.printStackTrace();
        }
    }


    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


public void switchToSignUp(ActionEvent event) throws IOException {
    ResourceBundle bundle = LocaleBundle.bundle(selectedLanguageCode);
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/signup.fxml"), bundle);
    root = loader.load();
    SignupController signupController = loader.getController();
    signupController.setSelectedLanguageCode(selectedLanguageCode);
    signupController.updateTexts(); // Call updateTexts() in the SignupController
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setTitle(Translate.get("signup.text"));
    stage.setScene(scene);
    stage.show();
}

    public void setSelectedLanguageCode(String languageCode) {
        selectedLanguageCode = languageCode;
    }

    public void switchToEnglish(ActionEvent event) {
        Translate.setLocale(Locale.ENGLISH);
        updateTexts();
    }


    public void switchToAlbanian(ActionEvent event) {
        Translate.setLocale(new Locale("sq"));
        updateTexts();
    }


    public static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] byteArray = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            byteArray[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return byteArray;
    }


}


