package service;

import controllers.FillimiController;
import controllers.SignupController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;
import repository.LoginRepository;
import repository.UserRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static controllers.LoginController.selectedLanguageCode;
import static service.PasswordUtil.showErrorAlert;

public class LoginService {
    private LoginRepository loginRepository;

    public static boolean loginEmptyFields(String idNumber, String password) {
        if (idNumber.isEmpty() || password.isEmpty()) {
            showErrorAlert(Translate.get("login.error.emptyFields"));
            return true;
        }
        return false;
    }

    public static void loginUser(String idNumber, String password, Parent root, Scene scene, TextField field){
        LoginRepository loginRepository1 = new LoginRepository();
        loginRepository1.loginUser(idNumber, password, root, scene, field);
    }



}






