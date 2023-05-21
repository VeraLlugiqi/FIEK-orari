package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.LocaleBundle;
import service.ConnectionUtil;
import service.PasswordUtil;
import service.Translate;

public class SignupController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField idNumberTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label signupLabel;
    @FXML
    private Button signUpButton;
    @FXML
    private Label questionSignupLabel;
    @FXML
    private Button loginButton;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTexts(); // Call updateTexts() during initialization
    }
    public void registerUser(){
        String idNumber = idNumberTextField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate if any field is empty
        if (idNumber.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            PasswordUtil.showErrorAlert(Translate.get("login.error.emptyFields"));
            return;
        }

        // Check if the ID number exists in the user table
        if (!isIdNumberValid(idNumber)) {
            PasswordUtil.showErrorAlert(Translate.get("login.error.invalidId"));
            return;
        }

        if (password.length() < 8) {
            PasswordUtil.showErrorAlert(Translate.get("login.error.passwordTooShort"));
            return;
        }

        // Validate password match
        if (!password.equals(confirmPassword)) {
            PasswordUtil.showErrorAlert(Translate.get("login.error.passwordMismatch"));
            return ;
        }

        // Generate salt
        byte[] salt = PasswordUtil.generateSalt();

        // Hash the password using the salt and the SHA-256 algorithm
        String saltedHashedPassword = PasswordUtil.hashPassword(password, salt);

        // Insert the password and salt into the database
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement("UPDATE user SET password = ?, salt = ? WHERE idNumber = ?")) {
            statement.setString(1, saltedHashedPassword);
            statement.setString(2, PasswordUtil.byteArrayToHexString(salt));
            statement.setString(3, idNumber);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                PasswordUtil.showAlert(Translate.get("login.RegisterdSuccesfully"));

                // Load the login.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/logIn.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) passwordField.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    PasswordUtil.showErrorAlert(Translate.get("login.error.loadScreen"));
                    e.printStackTrace();
                }
            } else {
                PasswordUtil.showErrorAlert(Translate.get("login.error.passwordTooShort"));
            }
        } catch (SQLException e) {
            PasswordUtil.showErrorAlert(Translate.get("login.error.PassowrdUpdateFail"));
            e.printStackTrace();
        }
    }

    private boolean isIdNumberValid(String idNumber) {
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM user WHERE idNumber = ?")) {
            statement.setString(1, idNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @FXML
    private void switchToLogin() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
//            Parent root = loader.load();
//            Scene scene = new Scene(root);
//            Stage stage = (Stage) signUpButton.getScene().getWindow();
//            stage.setScene(scene);
//
//            LoginController loginController = loader.getController();
//            loginController.updateTexts(); // Call updateTexts() in LoginController to update the texts
//
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void updateTexts() {
        signUpButton.setText(Translate.get("signUpButton"));
        questionSignupLabel.setText(Translate.get("questionSignupLabel"));
        loginButton.setText(Translate.get("loginButton"));
        signupLabel.setText(Translate.get("signupLabel"));
        passwordField.setPromptText(Translate.get("passwordPrompt"));
        confirmPasswordField.setPromptText(Translate.get("confirmPasswordPrompt"));
        idNumberTextField.setPromptText(Translate.get("idNumberPrompt"));
    }


}
