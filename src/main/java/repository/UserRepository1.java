package repository;

import controllers.FillimiService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.dto.PasswordDataDto;
import service.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static controllers.LoginController.hexStringToByteArray;
import static controllers.SignupController.selectedLanguageCode;
import static service.PasswordUtil.showErrorAlert;

public class UserRepository1 {

    //Register Repositories
    public void insertPassword(byte[] salt, String saltedHashedPassword, String idNumber, TextField passwordField){
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement("UPDATE user SET password = ?, salt = ? WHERE idNumber = ?")) {
            statement.setString(1, saltedHashedPassword);
            statement.setString(2, PasswordUtil.byteArrayToHexString(salt));
            statement.setString(3, idNumber);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                PasswordUtil.showAlert(getTranslation("login.RegisterdSuccesfully"));
                UserService1 signupService = new UserService1();
                signupService.loadFXML(passwordField);
            } else {
                PasswordUtil.showErrorAlert(getTranslation("login.error.passwordTooShort"));
            }
            conn.close();
            statement.close();

        } catch (SQLException e) {
            PasswordUtil.showErrorAlert(getTranslation("login.error.PassowrdUpdateFail"));
            e.printStackTrace();
        }
    }

    public static boolean isIdNumberValid(String idNumber) {
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM user WHERE idNumber = ?")) {
            statement.setString(1, idNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
            conn.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    private String getTranslation(String key) {
        return Translate.get(key, selectedLanguageCode);
    }

    //Login repositories
    Parent root;
    Scene scene;
    public void loginUser(
            String idNumber,
            String password,
            TextField idTextField
    )
    {
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM user WHERE idNumber = ?")) {
            statement.setString(1, idNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                byte[] salt = hexStringToByteArray(resultSet.getString("salt"));
                String hashedPassword = PasswordUtil.hashPassword(password, salt);

                if (storedPassword.equals(hashedPassword)) {
                    try {

                        UserService.loggedInUserId = idNumber;
                        System.out.println(UserService.loggedInUserId);

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/fillimi.fxml"));
                        root = loader.load();
                        FillimiService fillimiController = loader.getController();
                        fillimiController.setSelectedLanguageCode(UserRepository.selectedLanguageCode);

                        fillimiController.updateTexts(); // Call updateTexts() in the SignupController
                        Stage stage = (Stage) idTextField.getScene().getWindow();
//                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setTitle(Translate.get("signUpButton.text"));
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        showErrorAlert(Translate.get("login.error.loadScreen"));
                        e.printStackTrace();
                    }
                } else {
                    showErrorAlert(Translate.get("login.error.invalidCredentials"));
                }

            } else {
                showErrorAlert(Translate.get("login.error.invalidCredentials"));
            }
            conn.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            showErrorAlert(Translate.get("login.error.loginFailed"));
            e.printStackTrace();
        }
    }

    //Password Update repositories

    public static PasswordDataDto getPassword() {
        byte[] salt = null;
        String saltedHashedPassword = null;

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT salt, password FROM user WHERE idNumber = ?")) {
            statement.setString(1, UserService.loggedInUserId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                salt = PasswordUtil.hexStringToByteArray(resultSet.getString("salt"));
                saltedHashedPassword = resultSet.getString("password");
            } else {
                PasswordUtil.showErrorAlert("Invalid idNumber.");
                return null;
            }
        } catch (SQLException e) {
            PasswordUtil.showErrorAlert("Failed to retrieve user information. Please try again.");
            e.printStackTrace();
            return null;
        }

        return new PasswordDataDto(salt, saltedHashedPassword);
    }

    public static void updatePassword(
            String newSaltedHashedPassword,
            byte[] newSalt
    ){
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement("UPDATE user SET password = ?, salt = ? WHERE idNumber = ?")) {
            statement.setString(1, newSaltedHashedPassword);
            statement.setString(2, PasswordUtil.byteArrayToHexString(newSalt));
            statement.setString(3, UserService.loggedInUserId);
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

}
