package service;

import controllers.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.dto.PasswordDataDto;
import repository.UserRepository;

import java.io.IOException;
import java.util.ResourceBundle;

import static controllers.SignupController.selectedLanguageCode;
import static service.PasswordUtil.showErrorAlert;

public class UserService {
    public static String loggedInUserId;
    public static String selectedLanguageCode = "sq"; // Default language code

    //Register services
    Parent root; Scene scene; Stage stage;
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
            return;
        }
        return;
    }

    private boolean isIdNumberValid(String idNumber) {
        return UserRepository.isIdNumberValid(idNumber);
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
        UserRepository signupRepository = new UserRepository();
        signupRepository.insertPassword(salt, saltedHashedPassword, idNumber, passwordField);

    }

    public void switchToLogin(ActionEvent event) throws IOException {
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

    //Login services
    public static boolean loginEmptyFields(String idNumber, String password) {
        if (idNumber.isEmpty() || password.isEmpty()) {
            showErrorAlert(Translate.get("login.error.emptyFields"));
            return true;
        }
        return false;
    }

    public static void loginUser(String idNumber, String password, TextField field){
        UserRepository loginRepository1 = new UserRepository();
        loginRepository1.loginUser(idNumber, password, field);
    }

    //Password Update services
    public static boolean emptyFields(String currentPassword, String newPassword, String confirmPassword){
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            PasswordUtil.showErrorAlert("All fields are required.");
            return true;
        }
        return false;
    }

    public static boolean  passwordVerify(
            String currentSaltedHashedPassword,
            String saltedHashedPassword,
            String newPassword,
            String confirmPassword
    ){


        if (!currentSaltedHashedPassword.equals(saltedHashedPassword)) {
            PasswordUtil.showErrorAlert("Incorrect current password.");
            return true;
        }

        // Validate new password length
        if (newPassword.length() < 8) {
            PasswordUtil.showErrorAlert("New password must be at least 8 characters.");
            return true;
        }

        // Validate password match
        if (!newPassword.equals(confirmPassword)) {
            PasswordUtil.showErrorAlert("New passwords do not match.");
            return true;
        }
        return false;
    }

    public static void updatePassword(String newSaltedHashedPassword, byte[] newSalt){
        UserRepository.updatePassword(newSaltedHashedPassword, newSalt);
    }

    public static PasswordDataDto getPassword() {
        return UserRepository.getPassword();
    }


    }
