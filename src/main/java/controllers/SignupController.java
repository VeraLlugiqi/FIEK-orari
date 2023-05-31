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
import service.LocaleBundle;
import service.PasswordUtil;
import service.Translate;
import service.UserService;
import repository.UserRepository;

public class SignupController extends SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

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
    public UserService userService;

    public SignupController() {
        userService = new UserService();
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
            PasswordUtil.showErrorAlert(Translate.get("login.error.emptyFields"));
            return;
        }


        if (!userRepository.isIdNumberValid(idNumber)) {
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
            return;
        }

        boolean success = userService.registerUser(idNumber, password, confirmPassword);


        if (success) {
            PasswordUtil.showAlert(Translate.get("login.RegisterdSuccesfully"));

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
                PasswordUtil.showErrorAlert(Translate.get("login.error.loadScreen"));
                e.printStackTrace();
            }
        } else {
            PasswordUtil.showErrorAlert(Translate.get("login.error.passwordTooShort"));
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
        signUpButton.setText(Translate.get("signUpButton.text"));
        questionSignupLabel.setText(Translate.get("questionSignupLabel.text"));
        loginButton.setText(Translate.get("loginButton.text"));
        signupLabel.setText(Translate.get("signupLabel.text"));
        passwordField.setPromptText(Translate.get("passwordLabel.text"));
        confirmPasswordField.setPromptText(Translate.get("confirmPasswordField.promptText"));
        idNumberTextField.setPromptText(Translate.get("idNumberLabel.text"));
    }


}
