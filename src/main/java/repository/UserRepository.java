package repository;

        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;

        import models.User;
        import service.ConnectionUtil;
        import service.PasswordUtil;
        import service.Translate;

public class UserRepository {
    public static String selectedLanguageCode = "sq";
    public boolean isIdNumberValid(String idNumber) {
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM user WHERE idNumber = ?")) {
            statement.setString(1, idNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean registerUser(String idNumber, String password, String confirmPassword) {
        // Validate if any field is empty
        if (idNumber.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            PasswordUtil.showErrorAlert(getTranslation("login.error.emptyFields"));
            return false;
        }

        // Check if the ID number exists in the user table
        if (!isIdNumberValid(idNumber)) {
            PasswordUtil.showErrorAlert(getTranslation("login.error.invalidId"));
            return false;
        }

        if (password.length() < 8) {
            PasswordUtil.showErrorAlert(getTranslation("login.error.passwordTooShort"));
            return false;
        }

        // Validate password match
        if (!password.equals(confirmPassword)) {
            PasswordUtil.showErrorAlert(getTranslation("login.error.passwordMismatch"));
            return false;
        }

        // Generate salt
        byte[] salt = PasswordUtil.generateSalt();

        // Hash the password using the salt and the SHA-256 algorithm
        String saltedHashedPassword = PasswordUtil.hashPassword(password, salt);

        // Insert the password and salt into the database
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement("UPDATE user SET password = ?, salt = ? WHERE idNumber = ?")) {
            statement.setString(1, saltedHashedPassword);
            statement.setString(2, PasswordUtil.byteArrayToHexString(salt));
            statement.setString(3, idNumber);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                PasswordUtil.showAlert(getTranslation("login.RegisterdSuccesfully"));
                return true;
            } else {
                PasswordUtil.showErrorAlert(getTranslation("login.error.passwordTooShort"));
                return false;
            }
        } catch (SQLException e) {
            PasswordUtil.showErrorAlert(getTranslation("login.error.PassowrdUpdateFail"));
            e.printStackTrace();
            return false;
        }
    }
    public static User getUser(String idNumber) {
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM user WHERE idNumber = ?")) {
            statement.setString(1, idNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setIdNumber(resultSet.getString("idNumber"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTranslation(String key) {
        return Translate.get(key, selectedLanguageCode);
    }

        public boolean createUser(User user) {
            try (Connection conn = ConnectionUtil.getConnection();
                 PreparedStatement statement = conn.prepareStatement("INSERT INTO user (idNumber, password) VALUES (?, ?)")) {
                statement.setString(1, user.getIdNumber());
                statement.setString(2, user.getPassword());
                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

//        public User loginUser(String idNumber, String password) {
//            try (Connection conn = ConnectionUtil.getConnection();
//                 PreparedStatement statement = conn.prepareStatement("SELECT * FROM user WHERE idNumber = ?")) {
//                statement.setString(1, idNumber);
//                ResultSet resultSet = statement.executeQuery();
//                if (resultSet.next()) {
//                    String storedPassword = resultSet.getString("password");
//                    if (storedPassword.equals(password)) {
//                        User user = new User();
//                        user.setIdNumber(resultSet.getString("idNumber"));
//                        user.setPassword(resultSet.getString("password"));
//                        return user;
//                    }
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }

    public User loginUser(String idNumber, String password) {
        // Validate if any field is empty
        if (idNumber.isEmpty() || password.isEmpty()) {
            return null;
        }

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM user WHERE idNumber = ?")) {
            statement.setString(1, idNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                byte[] salt = PasswordUtil.hexStringToByteArray(resultSet.getString("salt"));
                String hashedPassword = PasswordUtil.hashPassword(password, salt);

                if (storedPassword.equals(hashedPassword)) {
                    User user = new User();
                    user.setIdNumber(resultSet.getString("idNumber"));
                    user.setPassword(resultSet.getString("password"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean updateUser(User user) {
            try (Connection conn = ConnectionUtil.getConnection();
                 PreparedStatement statement = conn.prepareStatement("UPDATE user SET password = ? WHERE idNumber = ?")) {
                statement.setString(1, user.getPassword());
                statement.setString(2, user.getIdNumber());
                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

