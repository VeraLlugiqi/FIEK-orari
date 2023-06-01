package service;


import javafx.scene.control.TextField;
import repository.LoginRepository;

import static service.PasswordUtil.showErrorAlert;

public class LoginService {
    public static boolean loginEmptyFields(String idNumber, String password) {
        if (idNumber.isEmpty() || password.isEmpty()) {
            showErrorAlert(Translate.get("login.error.emptyFields"));
            return true;
        }
        return false;
    }

    public static void loginUser(String idNumber, String password, TextField field){
        LoginRepository loginRepository1 = new LoginRepository();
        loginRepository1.loginUser(idNumber, password, field);
    }



}






