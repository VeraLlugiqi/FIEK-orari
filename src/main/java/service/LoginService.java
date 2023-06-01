package service;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import repository.LoginRepository;

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






