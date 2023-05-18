package com.example.fiekorari;


import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.ConnectionUtil;
import service.PasswordUtil;

public class SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private void switchToFillimi(ActionEvent event) throws IOException {
         root = FXMLLoader.load(getClass().getResource("fillimi.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Fillimi");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchToMenaxhoOret(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("menaxhoOret.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Menaxho Orët");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchToProfili(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("profili.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Profili");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchToLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("logIn.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Kyçu");
        stage.setScene(scene);
        stage.show();
    }




        @FXML
        private TextField idNumberTextField;
        @FXML
        private PasswordField passwordField;
        @FXML
        private PasswordField newPasswordField;
        @FXML
        private PasswordField confirmNewPasswordField;
        @FXML
        private Button saveChangesButton;

        @FXML
        private void saveChanges(ActionEvent event) {
            String idNumber = idNumberTextField.getText();
            String currentPassword = passwordField.getText();
            String newPassword = newPasswordField.getText();
            String confirmPassword = confirmNewPasswordField.getText();

            // Validate if any field is empty
            if (idNumber.isEmpty() || currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                PasswordUtil.showErrorAlert("All fields are required.");
                return;
            }

            // Retrieve the salt and hashed password from the database
            String saltedHashedPassword = null;
            byte[] salt = null;
            try (Connection conn = ConnectionUtil.getConnection();
                 PreparedStatement statement = conn.prepareStatement("SELECT salt, password FROM user WHERE idNumber = ?")) {
                statement.setString(1, idNumber);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    salt = resultSet.getBytes("salt");
                    saltedHashedPassword = resultSet.getString("password");
                } else {
                    PasswordUtil.showErrorAlert("Invalid idNumber.");
                    return;
                }
            } catch (SQLException e) {
                PasswordUtil.showErrorAlert("Failed to retrieve user information. Please try again.");
                e.printStackTrace();
                return;
            }

            // Verify current password
            if (!verifyPassword(currentPassword, salt, saltedHashedPassword)) {
                PasswordUtil.showErrorAlert("Incorrect current password.");
                return;
            }

            // Validate new password length
            if (newPassword.length() < 8) {
                PasswordUtil.showErrorAlert("New password must be at least 8 characters.");
                return;
            }

            // Validate password match
            if (!newPassword.equals(confirmPassword)) {
                PasswordUtil.showErrorAlert("New passwords do not match.");
                return;
            }

            // Generate new salt
            byte[] newSalt = PasswordUtil.generateSalt();

            // Hash the new password using the new salt and the SHA-256 algorithm
            String newSaltedHashedPassword = hashPassword(newPassword, newSalt);

            // Update the password in the database
            try (Connection conn = ConnectionUtil.getConnection();
                 PreparedStatement statement = conn.prepareStatement("UPDATE user SET password = ?, salt = ? WHERE idNumber = ?")) {
                statement.setString(1, newSaltedHashedPassword);
                statement.setString(2, PasswordUtil.byteArrayToHexString(newSalt));
                statement.setString(3, idNumber);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    PasswordUtil.showAlert("Password updated successfully.");
                } else {
                    PasswordUtil.showErrorAlert("Failed to update password. Please try again.");
                }
            } catch (SQLException e) {
                PasswordUtil.showErrorAlert("Failed to update password. Please try again.");
                e.printStackTrace();
            }
        }

        private boolean verifyPassword(String password, byte[] salt, String saltedHashedPassword) {
            // Hash the input password with the stored salt
            String hashedPassword = hashPassword(password, salt);

            // Compare the hashed password with the stored hashed password
            return hashedPassword.equals(saltedHashedPassword);
        }

        private String hashPassword(String password, byte[] salt) {
            try {
                // Create a MessageDigest instance with the SHA-256 algorithm
                MessageDigest digest = MessageDigest.getInstance("SHA-256");

                // Combine the password and salt
                byte[] passwordBytes = password.getBytes();
                byte[] saltedPasswordBytes = new byte[passwordBytes.length + salt.length];
                System.arraycopy(passwordBytes, 0, saltedPasswordBytes, 0, passwordBytes.length);
                System.arraycopy(salt, 0, saltedPasswordBytes, passwordBytes.length, salt.length);

                // Generate the hashed password
                byte[] hashedBytes = digest.digest(saltedPasswordBytes);

                // Convert the hashed bytes to a hexadecimal string
                return PasswordUtil.byteArrayToHexString(hashedBytes);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }


    @FXML
    private void switchToNdihma(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ndihma.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Ndihma");
        stage.setScene(scene);
        stage.show();
    }
    }
