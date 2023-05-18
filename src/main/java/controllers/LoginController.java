//<<<<<<< HEAD
//package controllers;
//
//
//import java.io.IOException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Base64;
//import javafx.scene.Parent;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//import service.ConnectionUtil;
//
//public class LoginController {
//    @FXML
//    private TextField idTextField;
//    @FXML
//    private PasswordField passwordField;
//    private Stage stage;
//    private Scene scene;
//    private Parent root;
//
//
//    public void loginUser() {
//        String idNumber = idTextField.getText();
//        String password = passwordField.getText();
//
//        // Validate if any field is empty
//        if (idNumber.isEmpty() || password.isEmpty()) {
//            showErrorAlert("ID Number and password are required.");
//            return;
//        }
//
//        // Retrieve the user from the database
//        try (Connection conn = ConnectionUtil.getConnection();
//             PreparedStatement statement = conn.prepareStatement("SELECT * FROM user WHERE idNumber = ?")) {
//            statement.setString(1, idNumber);
//            ResultSet resultSet = statement.executeQuery();
//
//            if (resultSet.next()) {
//                String storedPassword = resultSet.getString("password");
//                byte[] salt = hexStringToByteArray(resultSet.getString("salt"));
//                String hashedPassword = hashPassword(password, salt);
//
//                if (storedPassword.equals(hashedPassword)) {
//                    // Login successful, proceed to the next screen or action
//                    showAlert("Login successful!");
//                } else {
//                    showErrorAlert("Invalid idNumber or password.");
//                }
//            } else {
//                showErrorAlert("Invalid idNumber or password.");
//            }
//        } catch (SQLException e) {
//            showErrorAlert("Failed to login. Please try again.");
//            e.printStackTrace();
//        }
//    }
//
//    private String hashPassword(String password, byte[] salt) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            md.update(salt);
//            byte[] hashedPassword = md.digest(password.getBytes());
//            return Base64.getEncoder().encodeToString(hashedPassword);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("Failed to hash password.", e);
//        }
//    }
//
//    private void showAlert(String message) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//
//    private void showErrorAlert(String message) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//
//    private byte[] hexStringToByteArray(String hexString) {
//        int len = hexString.length();
//        byte[] data = new byte[len / 2];
//        for (int i = 0; i < len; i += 2) {
//            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
//                    + Character.digit(hexString.charAt(i + 1), 16));
//        }
//        return data;
//    }
//
//    public void switchToSignUp(ActionEvent event) throws IOException {
//        root = FXMLLoader.load(getClass().getResource("signup.fxml"));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setTitle("Regjistrohu");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//}
//=======
package controllers;


import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.ConnectionUtil;
import service.PasswordUtil;

public class LoginController {
    @FXML
    private TextField idTextField;
    @FXML
    private PasswordField passwordField;
    private Stage stage;
    private Scene scene;
    private Parent root;


    public void loginUser() {
        String idNumber = idTextField.getText();
        String password = passwordField.getText();

        // Validate if any field is empty
        if (idNumber.isEmpty() || password.isEmpty()) {
            showErrorAlert("ID Number and password are required.");
            return;
        }

        // Retrieve the user from the database
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM user WHERE idNumber = ?")) {
            statement.setString(1, idNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                byte[] salt = PasswordUtil.hexStringToByteArray(resultSet.getString("salt"));
                String hashedPassword = PasswordUtil.hashPassword(password, salt);

                if (storedPassword.equals(hashedPassword)) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/fillimi.fxml"));
                        root = loader.load();
                        stage = (Stage) idTextField.getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        showErrorAlert("Failed to load the next screen. Please try again.");
                        e.printStackTrace();
                    }
                } else {
                    showErrorAlert("Invalid idNumber or password.");
                }

            } else {
                showErrorAlert("Invalid idNumber or password.");
            }
        } catch (SQLException e) {
            showErrorAlert("Failed to login. Please try again.");
            e.printStackTrace();
        }
    }


    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    public void switchToSignUp(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/fiekorari/signup.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Regjistrohu");
        stage.setScene(scene);
        stage.show();
    }

}

