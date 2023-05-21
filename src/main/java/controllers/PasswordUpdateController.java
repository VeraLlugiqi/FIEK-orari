package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import service.ConnectionUtil;
import service.PasswordUtil;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordUpdateController extends SceneController {
    ActionEvent actionEvent;
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
    //@SuppressWarnings("unused")
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
                salt = PasswordUtil.hexStringToByteArray(resultSet.getString("salt"));
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
        // Hash the current password using the salt from the database
        String currentSaltedHashedPassword = PasswordUtil.hashPassword(currentPassword, salt);




        // Verify current password
        if (!currentSaltedHashedPassword.equals(saltedHashedPassword)) {
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
        String newSaltedHashedPassword = PasswordUtil.hashPassword(newPassword, newSalt);

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

    public void switchToFillimi() throws IOException{
        switchToFillimi(actionEvent);
    }
    public void switchToMenaxhoOret() throws IOException{
        switchToMenaxhoOret(actionEvent);
    }
    public void switchToProfili() throws IOException{
        switchToProfili(actionEvent);
    }
    public void switchToLogin() throws IOException{
        switchToLogin(actionEvent);
    }
    public void switchToNdihma() throws IOException {
        switchToNdihma(actionEvent);
    }
    public void switchToOrari() throws IOException{
        switchToOrari(actionEvent);
    }


    public void switchToClose(ActionEvent event) {
        System.exit(0);
    }
}


