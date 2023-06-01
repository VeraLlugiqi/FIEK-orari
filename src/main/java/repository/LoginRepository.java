package repository;

import controllers.FillimiService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static controllers.LoginController.hexStringToByteArray;
import static repository.UserRepository.selectedLanguageCode;
import static service.PasswordUtil.showErrorAlert;

public class LoginRepository {

    public boolean verifyCredentials(String idNumber, String password) {
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM user WHERE idNumber = ?");
            statement.setString(1, idNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                byte[] salt = PasswordUtil.hexStringToByteArray(resultSet.getString("salt"));
                String hashedPassword = PasswordUtil.hashPassword(password, salt);

                return storedPassword.equals(hashedPassword);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void loginUser(
            String idNumber,
            String password,
            Parent root,
            Scene scene,
            TextField idTextField
    ){
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
                        fillimiController.setSelectedLanguageCode(selectedLanguageCode);

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
        } catch (SQLException e) {
            showErrorAlert(Translate.get("login.error.loginFailed"));
            e.printStackTrace();
        }
    }
}
