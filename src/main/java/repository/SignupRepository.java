package repository;

import javafx.scene.control.TextField;
import models.UserModel;
import service.*;
import static controllers.SignupController.selectedLanguageCode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignupRepository{
    public static UserModel getById(String id) throws SQLException {
        String sql = "SELECT * FROM users WHERE idNumber=?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int uid = resultSet.getInt("uid");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    String idNumber = resultSet.getString("idNumber");
                    String password = resultSet.getString("password");
                    String salt = resultSet.getString("salt");
                    return new UserModel(uid, firstName, lastName, idNumber, password, salt);
                } else {
                    return null;
                }
            }
        }
    }

//    public static UserModel update(CreateUserDto user) throws SQLException {
//            try (Connection conn = ConnectionUtil.getConnection();
//                 PreparedStatement statement = conn.prepareStatement("UPDATE user SET password = ?, salt = ? WHERE idNumber = ?")) {
//                statement.setString(1, user.getPassword());
//                statement.setString(2, PasswordUtil.byteArrayToHexString(user.getHashedPassword()));
//                statement.setString(3, user.getIdNumber());
//                int rowsAffected = statement.executeUpdate();
//            // Create and return the new User object directly
//            // return new User(user.getEmri(), user.getMbiemri(), user.getEmail(), user.getUsername(), user.getSaltedPassword(), user.getSalt());
//        } catch (SQLException e) {
//            // Handle exceptions
//            throw e;
//        }
//
//            return getById(user.getIdNumber());
//
//        }

        public void insertPassword(byte[] salt, String saltedHashedPassword, String idNumber, TextField passwordField){
            try (Connection conn = ConnectionUtil.getConnection();
                 PreparedStatement statement = conn.prepareStatement("UPDATE user SET password = ?, salt = ? WHERE idNumber = ?")) {
                statement.setString(1, saltedHashedPassword);
                statement.setString(2, PasswordUtil.byteArrayToHexString(salt));
                statement.setString(3, idNumber);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    PasswordUtil.showAlert(getTranslation("login.RegisterdSuccesfully"));
                    SignupService signupService = new SignupService();
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
}
