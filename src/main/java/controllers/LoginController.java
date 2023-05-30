
package controllers;


import java.io.IOException;
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
import repository.LocaleBundle;
import service.ConnectionUtil;
import service.PasswordUtil;
import service.UserService;

public class LoginController {
    private UserService userController;


    public LoginController() {
        userController = new UserService();
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

                        UserService.loggedInUserId = idNumber;
                        System.out.println(UserService.loggedInUserId);

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


//package controllers;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
//import javafx.stage.Stage;
//import models.User;
//import service.LoginService;
//import service.Translate;
//
//import java.io.IOException;
//import java.util.Locale;
//
//public class LoginController {
//    private LoginService loginService;
//
//    public LoginController() {
//        loginService = new LoginService();
//    }
//
//    @FXML
//    private TextField idTextField;
//    @FXML
//    private PasswordField passwordField;
//
//    @FXML
//    private Label fiek_orariLabel;
//    @FXML
//    private Button loginButton;
//    @FXML
//    private Button signUpButton;
//    @FXML
//    private Label notRegisteredLabel;
//
//    private Stage stage;
//    private Scene scene;
//    private Parent root;
//
//    public static String selectedLanguageCode = "sq"; // Default language code
//
//    public void updateTexts() {
//        fiek_orariLabel.setText(Translate.get("fiek_orariLabel.text"));
//        idTextField.setPromptText(Translate.get("idTextField.promptText"));
//        passwordField.setPromptText(Translate.get("passwordField.promptText"));
//        loginButton.setText(Translate.get("loginButton.text"));
//        notRegisteredLabel.setText(Translate.get("notRegisteredLabel.text"));
//        signUpButton.setText(Translate.get("signUpButton.text"));
//    }
//
//
//
//    public void performLogin() {
//        String idNumber = idTextField.getText();
//        String password = passwordField.getText();
//
//        // Call the login service to authenticate the user
//        User user = loginService.loginUser(idNumber, password);
//
//        if (user != null) {
//            // Successful login
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/fillimi.fxml"));
//                root = loader.load();
//                FillimiController fillimiController = loader.getController();
//                fillimiController.setSelectedLanguageCode(selectedLanguageCode);
//                fillimiController.updateTexts();
//                stage = (Stage) idTextField.getScene().getWindow();
//                scene = new Scene(root);
//                stage.setTitle(Translate.get("fillimi.text"));
//                stage.setScene(scene);
//                stage.show();
//            } catch (IOException e) {
//                showErrorAlert(Translate.get("login.error.loadScreen"));
//                e.printStackTrace();
//            }
//        } else {
//            // Invalid credentials
//            showErrorAlert(Translate.get("login.error.invalidCredentials"));
//        }
//    }
//
//    public void loginUserWithEnter(KeyEvent event) {
//        if (event.getCode() == KeyCode.ENTER) {
//            performLogin();
//        }
//    }
//    public void loginUser(ActionEvent event) {
//        performLogin();
//    }
//
//    private void showErrorAlert(String message) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//
//    public void switchToSignUp(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/signup.fxml"));
//        root = loader.load();
//        SignupController signupController = loader.getController();
//        signupController.setSelectedLanguageCode(selectedLanguageCode);
//        signupController.updateTexts();
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setTitle(Translate.get("signup.text"));
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public void setSelectedLanguageCode(String languageCode) {
//        selectedLanguageCode = languageCode;
//    }
//
//    public void switchToEnglish(ActionEvent event) {
//        Translate.setLocale(Locale.ENGLISH);
//        updateTexts();
//    }
//
//    public void switchToAlbanian(ActionEvent event) {
//        Translate.setLocale(new Locale("sq", "AL"));
//        updateTexts();
//    }
//
//    public static byte[] hexStringToByteArray(String hexString) {
//        int len = hexString.length();
//        byte[] data = new byte[len / 2];
//        for (int i = 0; i < len; i += 2) {
//            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
//                    + Character.digit(hexString.charAt(i + 1), 16));
//        }
//        return data;
//    }
//
//
//}
