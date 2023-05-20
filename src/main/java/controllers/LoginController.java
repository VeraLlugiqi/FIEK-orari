
package controllers;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.User;
import service.ConnectionUtil;
import service.PasswordUtil;

public class LoginController {
    private UserController userController;
    private ResourceBundle resources;
    public LoginController(){
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

    public void initialize() {
        loadLanguage("en");
    }
    public void loadLanguage(String language) {
        Locale locale = new Locale(language);
        resources = ResourceBundle.getBundle("translations/content", locale);
        updateTexts();
    }
    private void updateTexts() {
        fiek_orariLabel.setText(resources.getString("fiek_orariLabel.text"));
        idTextField.setPromptText(resources.getString("idTextField.promptText"));
        passwordField.setPromptText(resources.getString("passwordField.promptText"));
        loginButton.setText(resources.getString("loginButton.text"));
        notRegisteredLabel.setText(resources.getString("notRegisteredLabel.text"));
        signUpButton.setText(resources.getString("signUpButton.text"));
        }

    public void loginUser() {
        String idNumber = idTextField.getText();
        String password = passwordField.getText();

        // Validate if any field is empty
        if (idNumber.isEmpty() || password.isEmpty()) {
            showErrorAlert(resources.getString("login.error.emptyFields"));
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
                        stage = (Stage) idTextField.getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        showErrorAlert(resources.getString("login.error.loadScreen"));
                        e.printStackTrace();
                    }
                } else {
                    showErrorAlert(resources.getString("login.error.invalidCredentials"));
                }

            } else {
                showErrorAlert(resources.getString("login.error.invalidCredentials"));
            }
        } catch (SQLException e) {
            showErrorAlert(resources.getString("login.error.loginFailed"));
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
        root = FXMLLoader.load(getClass().getResource("/com/example/fiekorari/signup.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle(resources.getString("signupButton.text"));
        stage.setScene(scene);
        stage.show();
    }

    public void switchToEnglish(ActionEvent event) {
        loadLanguage("en");
    }

    public void switchToAlbanian(ActionEvent event) {
        loadLanguage("sq");
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


