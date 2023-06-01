package repository;

import models.dto.PasswordDataDto;
import service.ConnectionUtil;
import service.PasswordUtil;
import service.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordUpdateRepository {

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
