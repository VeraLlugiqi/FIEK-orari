package controllers;

<<<<<<< Updated upstream
public class SignupController {
}
=======
import java.io.IOException;
import java.security.MessageDigest;
        import java.security.NoSuchAlgorithmException;
        import java.security.SecureRandom;
        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.SQLException;
        import java.util.Base64;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
        import javafx.scene.control.Label;
        import javafx.scene.control.PasswordField;
        import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.ConnectionUtil;

public class SignupController {
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField idNumberTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label signupLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void registerUser(){
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String idNumber = idNumberTextField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate if any field is empty
        if (firstName.isEmpty() || lastName.isEmpty() || idNumber.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showErrorAlert("All fields are required.");
            return;
        }

        // Validate password match
        if (!password.equals(confirmPassword)) {
            showErrorAlert("Password does not match.");
            return ;
        }

        // Generate salt
        byte[] salt = generateSalt();

        // Hash the password using the salt and the SHA-256 algorithm
        String saltedHashedPassword = hashPassword(password, salt);

        // Insert the user into the database
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement("INSERT INTO user (firstName, lastName, idNumber, password, salt) VALUES (?, ?, ?, ?, ?)")) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, idNumber);
            statement.setString(4, saltedHashedPassword);
            statement.setString(5, byteArrayToHexString(salt));
            statement.executeUpdate();
            showAlert("User registered successfully.");
        } catch (SQLException e) {
            showErrorAlert("Failed to register user. Please try again.");
            e.printStackTrace();
        }
    }

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to hash password.", e);
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public void switchBackToLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("logIn.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Kyçu");
        stage.setScene(scene);
        stage.show();
    }
}



>>>>>>> Stashed changes
