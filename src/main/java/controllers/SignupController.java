//package controllers;
//
//import java.io.IOException;
//import java.net.URL;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ResourceBundle;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
//import javafx.stage.Stage;
//import repository.LocaleBundle;
//import service.ConnectionUtil;
//import service.PasswordUtil;
//import service.Translate;
////import service.getTranslation;
//
//public class SignupController extends SceneController {
//
//    private Stage stage;
//    private Scene scene;
//    private Parent root;
//
//    public static String selectedLanguageCode = "sq";
//    @FXML
//    private TextField idNumberTextField;
//    @FXML
//    private PasswordField passwordField;
//    @FXML
//    private PasswordField confirmPasswordField;
//    @FXML
//    private Label signupLabel;
//    @FXML
//    private Button signUpButton;
//    @FXML
//    private Label questionSignupLabel;
//    @FXML
//    private Button loginButton;
//
//    @FXML
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        updateTexts(); // Call updateTexts() during initialization
//    }
//
//    public void setSelectedLanguageCode(String languageCode) {
//        selectedLanguageCode = languageCode;
//    }
//
//    public void signupUser(ActionEvent event) {
//        registerUser();
//    }
//
//    public void signupUserWithEnter(KeyEvent event) {
//        if (event.getCode() == KeyCode.ENTER) {
//            registerUser();
//        }
//    }
//
//
//    public void registerUser(){
//        String idNumber = idNumberTextField.getText();
//        String password = passwordField.getText();
//        String confirmPassword = confirmPasswordField.getText();
//
//        // Validate if any field is empty
//        if (idNumber.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
//            PasswordUtil.showErrorAlert(getTranslation("login.error.emptyFields"));
//            return;
//        }
//
//        // Check if the ID number exists in the user table
//        if (!isIdNumberValid(idNumber)) {
//            PasswordUtil.showErrorAlert(getTranslation("login.error.invalidId"));
//            return;
//
//        }
//
//        if (password.length() < 8) {
//            PasswordUtil.showErrorAlert(getTranslation("login.error.passwordTooShort"));
//            return;
//        }
//
//        // Validate password match
//        if (!password.equals(confirmPassword)) {
//            PasswordUtil.showErrorAlert(getTranslation("login.error.passwordMismatch"));
//            return ;
//        }
//
//        // Generate salt
//        byte[] salt = PasswordUtil.generateSalt();
//
//        // Hash the password using the salt and the SHA-256 algorithm
//        String saltedHashedPassword = PasswordUtil.hashPassword(password, salt);
//
//        // Insert the password and salt into the database
//        try (Connection conn = ConnectionUtil.getConnection();
//             PreparedStatement statement = conn.prepareStatement("UPDATE user SET password = ?, salt = ? WHERE idNumber = ?")) {
//            statement.setString(1, saltedHashedPassword);
//            statement.setString(2, PasswordUtil.byteArrayToHexString(salt));
//            statement.setString(3, idNumber);
//            int rowsAffected = statement.executeUpdate();
//            if (rowsAffected > 0) {
//                PasswordUtil.showAlert(getTranslation("login.RegisterdSuccesfully"));
//
//                // Load the login.fxml file
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/logIn.fxml"));
//                try {
//                    Parent root = loader.load();
//                    Stage stage = (Stage) passwordField.getScene().getWindow();
//                    Scene scene = new Scene(root);
//                    stage.setScene(scene);
//                    stage.show();
//                } catch (IOException e) {
//                    PasswordUtil.showErrorAlert(getTranslation("login.error.loadScreen"));
//                    e.printStackTrace();
//                }
//            } else {
//                PasswordUtil.showErrorAlert(getTranslation("login.error.passwordTooShort"));
//            }
//        } catch (SQLException e) {
//            PasswordUtil.showErrorAlert(getTranslation("login.error.PassowrdUpdateFail"));
//            e.printStackTrace();
//        }
//    }
//
//    private boolean isIdNumberValid(String idNumber) {
//        try (Connection conn = ConnectionUtil.getConnection();
//             PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM user WHERE idNumber = ?")) {
//            statement.setString(1, idNumber);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                int count = resultSet.getInt(1);
//                return count > 0;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//
//
//    public void switchToLogin(ActionEvent event) throws IOException {
//        // Load the login screen FXML using the selected language bundle
//        ResourceBundle bundle = LocaleBundle.bundle(selectedLanguageCode);
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/login.fxml"), bundle);
//        root = loader.load();
//        LoginController loginController = loader.getController();
//        loginController.setSelectedLanguageCode(selectedLanguageCode);
//
//        loginController.updateTexts(); // Call updateTexts() in the LoginController
//
//        // Switch to the login screen
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public void updateTexts() {
//        signUpButton.setText(getTranslation("signUpButton.text"));
//        questionSignupLabel.setText(getTranslation("questionSignupLabel.text"));
//        loginButton.setText(getTranslation("loginButton.text"));
//        signupLabel.setText(getTranslation("signupLabel.text"));
//        passwordField.setPromptText(getTranslation("passwordLabel.text"));
//        confirmPasswordField.setPromptText(getTranslation("confirmPasswordField.promptText"));
//        idNumberTextField.setPromptText(getTranslation("idNumberLabel.text"));
//    }
//    private String getTranslation(String key) {
//        return Translate.get(key, selectedLanguageCode);
//    }
//
//}

package controllers;

import java.io.IOException;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import repository.LocaleBundle;
import service.PasswordUtil;
import service.Translate;
import service.SignupService;
import repository.UserRepository;

public class SignupController extends SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private SignupService signupService;
    private String selectedLanguageCode = "sq";

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
    UserRepository userRepository = new UserRepository();
    public SignupController() {
        this.signupService = new SignupService();
    }

    public void setSelectedLanguageCode(String languageCode) {
        this.selectedLanguageCode = languageCode;
    }

    @FXML
    public void initialize() {
        updateTexts(); // Call updateTexts() during initialization
    }

    @FXML
    public void signupUser(ActionEvent event) {
        registerUser();
    }

    @FXML
    public void signupUserWithEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            registerUser();
        }
    }

    private void registerUser() {
        String idNumber = idNumberTextField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate if any field is empty
        if (idNumber.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            PasswordUtil.showErrorAlert(userRepository.getTranslation("login.error.emptyFields"));
            return;
        }


        if (!userRepository.isIdNumberValid(idNumber)) {
            PasswordUtil.showErrorAlert(userRepository.getTranslation("login.error.invalidId"));
            return;
        }



        if (password.length() < 8) {
            PasswordUtil.showErrorAlert(userRepository.getTranslation("login.error.passwordTooShort"));
            return;
        }

        // Validate password match
        if (!password.equals(confirmPassword)) {
            PasswordUtil.showErrorAlert(userRepository.getTranslation("login.error.passwordMismatch"));
            return;
        }

        boolean success = signupService.registerUser(idNumber, password, confirmPassword);

        if (success) {
            PasswordUtil.showAlert(userRepository.getTranslation("login.RegisterdSuccesfully"));

            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/logIn.fxml"));
            loader.setResources(LocaleBundle.bundle(selectedLanguageCode));
            try {
                Parent root = loader.load();
                Stage stage = (Stage) passwordField.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                PasswordUtil.showErrorAlert(userRepository.getTranslation("login.error.loadScreen"));
                e.printStackTrace();
            }
        } else {
            PasswordUtil.showErrorAlert(userRepository.getTranslation("login.error.passwordTooShort"));
        }
    }

    @FXML
    public void switchToLogin(ActionEvent event) throws IOException {
        // Load the login screen FXML using the selected language bundle
        ResourceBundle bundle = LocaleBundle.bundle(selectedLanguageCode);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/logIn.fxml"), bundle);
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void updateTexts() {
        signUpButton.setText(userRepository.getTranslation("signUpButton.text"));
        questionSignupLabel.setText(userRepository.getTranslation("questionSignupLabel.text"));
        loginButton.setText(userRepository.getTranslation("loginButton.text"));
        signupLabel.setText(userRepository.getTranslation("signupLabel.text"));
        passwordField.setPromptText(userRepository.getTranslation("passwordLabel.text"));
        confirmPasswordField.setPromptText(userRepository.getTranslation("confirmPasswordField.promptText"));
        idNumberTextField.setPromptText(userRepository.getTranslation("idNumberLabel.text"));
    }


}
