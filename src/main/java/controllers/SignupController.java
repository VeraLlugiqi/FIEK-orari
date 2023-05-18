package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.ConnectionUtil;
import service.PasswordUtil;

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

    public void registerUser(){
        String idNumber = idNumberTextField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate if any field is empty
        if (idNumber.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            PasswordUtil.showErrorAlert("All fields are required.");
            return;
        }

        // Check if the ID number exists in the user table
        if (!isIdNumberValid(idNumber)) {
            PasswordUtil.showErrorAlert("Invalid ID number.");
            return;
        }

        if (password.length() < 8) {
            PasswordUtil.showErrorAlert("Password must be at least 8 characters.");
            return;
        }

        // Validate password match
        if (!password.equals(confirmPassword)) {
            PasswordUtil.showErrorAlert("Password does not match.");
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
                PasswordUtil.showAlert("Registred successfully.");

                // Load the login.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/logIn.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) passwordField.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    PasswordUtil.showErrorAlert("Failed to load login page. Please try again.");
                    e.printStackTrace();
                }
            } else {
                PasswordUtil.showErrorAlert("Failed to update password. Please try again.");
            }
        } catch (SQLException e) {
            PasswordUtil.showErrorAlert("Failed to update password. Please try again.");
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

    public void switchBackToLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/fiekorari/logIn.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
