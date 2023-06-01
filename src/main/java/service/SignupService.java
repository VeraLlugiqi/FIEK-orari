package service;

import controllers.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import repository.SignupRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static controllers.SignupService.selectedLanguageCode;

public class SignupService {
    private String getTranslation(String key) {
        return Translate.get(key, selectedLanguageCode);
    }
    public void signupValidation(String idNumber, String password, String confirmPassword){
        if (idNumber.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            PasswordUtil.showErrorAlert(getTranslation("login.error.emptyFields"));
            return;
        }

        // Check if the ID number exists in the user table
        if (!isIdNumberValid(idNumber)) {
            PasswordUtil.showErrorAlert(getTranslation("login.error.invalidId"));
            return;

        }

        if (password.length() < 8) {
            PasswordUtil.showErrorAlert(getTranslation("login.error.passwordTooShort"));
            return;
        }

        // Validate password match
        if (!password.equals(confirmPassword)) {
            PasswordUtil.showErrorAlert(getTranslation("login.error.passwordMismatch"));
            return ;
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

    public void loadFXML(TextField passwordField){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/logIn.fxml"));
        try {
            Parent root = loader.load();
            Stage stage = (Stage) passwordField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            PasswordUtil.showErrorAlert(getTranslation("login.error.loadScreen"));
            e.printStackTrace();
        }
    }
    public static void insertPassword(byte[] salt, String saltedHashedPassword, String idNumber, TextField passwordField){
        SignupRepository signupRepository = new SignupRepository();
        signupRepository.insertPassword(salt, saltedHashedPassword, idNumber, passwordField);

    }


    public void switchToLogin(ActionEvent event, Parent root, Scene scene, Stage stage) throws IOException {
        // Load the login screen FXML using the selected language bundle
        ResourceBundle bundle = LocaleBundle.bundle(selectedLanguageCode);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/login.fxml"), bundle);
        root = loader.load();
        LoginController loginController = loader.getController();
        loginController.setSelectedLanguageCode(selectedLanguageCode);

        loginController.updateTexts(); // Call updateTexts() in the LoginController

        // Switch to the login screen
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
