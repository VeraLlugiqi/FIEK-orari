package models.dto;

public class CreateUserDto {
    public String idNumber;
    public String password;
    public byte[] hashedPassword;

    public CreateUserDto(String idNumber, String password, byte[] hashedPassword){
        this.idNumber = idNumber;
        this.password = password;
        this.hashedPassword = hashedPassword;
    }


    public String getIdNumber() { return idNumber;}

    public void setIdNumber(String idNumber){
        this.idNumber = idNumber;
    }

    public String getPassword() { return password;}

    public void setPassword(String password){
        this.password = password;
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(byte[] hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}
