package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import service.ConnectionUtil;
import service.PasswordUtil;
import service.Translate;
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PasswordUpdateController extends SceneController {
    ActionEvent actionEvent;

    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmNewPasswordField;
    @FXML
    Label fiek_orariLabel;
    @FXML
    Button startButton;
    @FXML
    Button manageClassButton;
    @FXML
    Button profileButton;
    @FXML
    Button Oraributton;
    @FXML
    Button NdihmaButton;
    @FXML
    Button logoutButton;
    @FXML
    private Button saveChangesButton;
    @FXML
    Label profileTextLabel;
    @FXML
    Label profileLabel;
    @FXML
    Label repeatPasswordLabel;
    @FXML
    Label newPasswordLabel;
    @FXML
    Label passwordLabel;
    @FXML
    Label idNumberLabel;

    public static String selectedLanguageCode = "sq";

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTexts(); // Call updateTexts() during initialization
    }


    public void updatePassword(ActionEvent event) {
        saveChanges();
    }

    public void updatePasswordWithEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            saveChanges();
        }
    }


    private void saveChanges() {
        String currentPassword = passwordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmNewPasswordField.getText();

        // Validate if any field is empty
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            PasswordUtil.showErrorAlert(Translate.get("login.error.emptyFields"));
            return;
        }

        // Retrieve the salt and hashed password from the database
        String saltedHashedPassword = null;
        byte[] salt = null;
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT salt, password FROM user WHERE idNumber = ?")) {
            statement.setString(1, UserService.loggedInUserId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                salt = PasswordUtil.hexStringToByteArray(resultSet.getString("salt"));
                saltedHashedPassword = resultSet.getString("password");
            } else {
                PasswordUtil.showErrorAlert(Translate.get("login.error.invalidId"));
                return;
            }
        } catch (SQLException e) {
            PasswordUtil.showErrorAlert("DeshtimMarrjesDhenave.text");
            e.printStackTrace();
            return;
        }
        // Hash the current password using the salt from the database
        String currentSaltedHashedPassword = PasswordUtil.hashPassword(currentPassword, salt);




        // Verify current password
        if (!currentSaltedHashedPassword.equals(saltedHashedPassword)) {
            PasswordUtil.showErrorAlert(Translate.get("passwordGabim.text"));
            return;
        }

        // Validate new password length
        if (newPassword.length() < 8) {
            PasswordUtil.showErrorAlert(Translate.get("login.error.passwordTooShort"));
            return;
        }

        // Validate password match
        if (!newPassword.equals(confirmPassword)) {
            PasswordUtil.showErrorAlert(Translate.get("login.error.passwordMismatch"));
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
            statement.setString(3, UserService.loggedInUserId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                PasswordUtil.showAlert(Translate.get("updatedSuccesfuly.text"));
            } else {
                PasswordUtil.showErrorAlert(Translate.get("login.error.PassowrdUpdateFail"));
            }
        } catch (SQLException e) {
            PasswordUtil.showErrorAlert(Translate.get("login.error.PassowrdUpdateFail"));
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

    public void updateTexts() {
        logoutButton.setText(Translate.get("logoutButton.text"));
        NdihmaButton.setText(Translate.get("NdihmaButton.text"));
        Oraributton.setText(Translate.get("Oraributton.text"));
        profileButton.setText(Translate.get("profileButton.text"));
        manageClassButton.setText(Translate.get("manageClassButton.text"));
        startButton.setText(Translate.get("startButton.text"));
        fiek_orariLabel.setText(Translate.get("fiek_orariLabel.text"));
        profileTextLabel.setText(getTranslation("profileTextLabel.text"));
        profileLabel.setText(getTranslation("profileLabel.text"));
        saveChangesButton.setText(getTranslation("saveChangesButton.text"));
        repeatPasswordLabel.setText(getTranslation("repeatPasswordLabel.text"));
        newPasswordLabel.setText(getTranslation("newPasswordLabel.text"));
        passwordLabel.setText(getTranslation("passwordLabel.text"));
        // idNumberLabel.setText(getTranslation("idNumberLabel.text"));

    }
    private String getTranslation(String key) {
        return Translate.get(key, selectedLanguageCode);
    }

    public void setSelectedLanguageCode(String languageCode) {
        selectedLanguageCode = languageCode;
    }


}


