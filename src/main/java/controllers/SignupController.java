package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import service.*;
public class SignupController extends SceneService implements Initializable {
    @FXML
    private TextField idNumberTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTexts(); // Call updateTexts() during initialization
    }

    public void setSelectedLanguageCode(String languageCode) {
        selectedLanguageCode = languageCode;
    }

    public void registerUser(){
        String idNumber = idNumberTextField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate if any field is empty
        service.UserService1 signupService = new service.UserService1();
        signupService.signupValidation(idNumber, password, confirmPassword);
        // Generate salt
        byte[] salt = PasswordUtil.generateSalt();
        // Hash the password using the salt and the SHA-256 algorithm
        String saltedHashedPassword = PasswordUtil.hashPassword(password, salt);
        // Insert the password and salt into the database
        service.UserService1.insertPassword(salt, saltedHashedPassword, idNumber, passwordField);
    }

    public static Locale getSelectedLanguage() {
        return selectedLanguage;
    }

    public void switchToLogin(ActionEvent event) throws IOException {
        service.SignupService signupService = new service.SignupService();
        signupService.switchToLogin(event);
    }


//----------------Gjuha-----------------------
    @FXML
    private Label signupLabel;
    @FXML
    private Button signUpButton;
    @FXML
    private Label questionSignupLabel;
    @FXML
    private Button loginButton;

    public void updateTexts() {
        signUpButton.setText(getTranslation("signUpButton.text"));
        questionSignupLabel.setText(getTranslation("questionSignupLabel.text"));
        loginButton.setText(getTranslation("loginButton.text"));
        signupLabel.setText(getTranslation("signupLabel.text"));
        passwordField.setPromptText(getTranslation("passwordLabel.text"));
        confirmPasswordField.setPromptText(getTranslation("confirmPasswordField.promptText"));
        idNumberTextField.setPromptText(getTranslation("idNumberLabel.text"));
    }
    private String getTranslation(String key) {
        return Translate.get(key, selectedLanguageCode);
    }

    public void signupUser(ActionEvent event) {
        registerUser();
    }

    public void signupUserWithEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            registerUser();
        }
    }
    private static Locale selectedLanguage;
    public static String selectedLanguageCode = "sq";
}