package models.dto;

public class PasswordDataDto{
    private byte[] salt;
    private String saltedHashedPassword;

    public PasswordDataDto(byte[] salt, String saltedHashedPassword) {
        this.salt = salt;
        this.saltedHashedPassword = saltedHashedPassword;
    }

    public byte[] getSalt() {
        return salt;
    }

    public String getSaltedHashedPassword() {
        return saltedHashedPassword;
    }
}