package service;

import models.User;
import repository.UserRepository;

public class PasswordUpdateService {
    public static boolean emptyFields(String currentPassword, String newPassword, String confirmPassword){
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            PasswordUtil.showErrorAlert("All fields are required.");
            return true;
        }
        return false;
    }

    public static boolean  passwordVerify(
            String currentSaltedHashedPassword,
            String saltedHashedPassword,
            String newPassword,
            String confirmPassword
    ){


        if (!currentSaltedHashedPassword.equals(saltedHashedPassword)) {
            PasswordUtil.showErrorAlert("Incorrect current password.");
            return true;
        }

        // Validate new password length
        if (newPassword.length() < 8) {
            PasswordUtil.showErrorAlert("New password must be at least 8 characters.");
            return true;
        }

        // Validate password match
        if (!newPassword.equals(confirmPassword)) {
            PasswordUtil.showErrorAlert("New passwords do not match.");
            return true;
        }
        return false;
    }
}
