package service;

        import javafx.scene.control.Alert;
        import java.security.MessageDigest;
        import java.security.NoSuchAlgorithmException;
        import java.security.SecureRandom;
        import java.util.Base64;

public class PasswordUtil {

    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] passwordBytes = password.getBytes();
            byte[] saltedPasswordBytes = new byte[passwordBytes.length + salt.length];
            System.arraycopy(passwordBytes, 0, saltedPasswordBytes, 0, passwordBytes.length);
            System.arraycopy(salt, 0, saltedPasswordBytes, passwordBytes.length, salt.length);
            byte[] hashedBytes = digest.digest(saltedPasswordBytes);
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static boolean verifyPassword(String password, String hashedPassword, String hexSalt) {
        byte[] salt = hexStringToByteArray(hexSalt);
        String calculatedHash = hashPassword(password, salt);
        return hashedPassword.equals(calculatedHash);
    }

    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }


    public static byte[] hexStringToByteArray(String hexString) {
        if (hexString == null) {
            // Handle the null case here, such as returning an empty byte array or throwing an exception.
            // Example: throw new IllegalArgumentException("hexString cannot be null");
            return new byte[0];
        }
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}


