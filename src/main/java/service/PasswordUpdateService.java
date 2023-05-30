package service;

import models.User;
import repository.UserRepository;

public class PasswordUpdateService {
    private final UserRepository userRepository;

    public PasswordUpdateService() {
        this.userRepository = new UserRepository();
    }

    public boolean updatePassword(String idNumber, String currentPassword, String newPassword, String confirmPassword) {
        User user = userRepository.getUser(idNumber);

        if (user == null) {
            PasswordUtil.showErrorAlert(Translate.get("login.error.invalidId"));
            return false;
        }

        // Verify current password
        if (!PasswordUtil.verifyPassword(currentPassword, user.getPassword(), user.getSalt())) {
            PasswordUtil.showErrorAlert(Translate.get("passwordGabim.text"));
            return false;
        }

        // Validate new password length
        if (newPassword.length() < 8) {
            PasswordUtil.showErrorAlert(Translate.get("login.error.passwordTooShort"));
            return false;
        }

        // Validate password match
        if (!newPassword.equals(confirmPassword)) {
            PasswordUtil.showErrorAlert(Translate.get("login.error.passwordMismatch"));
            return false;
        }

        // Generate new salt
        byte[] newSalt = PasswordUtil.generateSalt();

        // Hash the new password using the new salt and the SHA-256 algorithm
        String newSaltedHashedPassword = PasswordUtil.hashPassword(newPassword, newSalt);

        // Update the password in the database
        user.setPassword(newSaltedHashedPassword);
        user.setSalt(PasswordUtil.byteArrayToHexString(newSalt));

        return userRepository.updateUser(user);
    }
}
