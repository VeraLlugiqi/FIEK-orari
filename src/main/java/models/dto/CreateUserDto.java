package models.dto;

public class CreateUserDto {
    private String idNumber;
    private String password;
   // private String salt;

    private CreateUserDto(String idNumber, String password){
        this.idNumber = idNumber;
        this.password = password;
    }


    public String getIdNumber() { return idNumber;}

    public void setIdNumber(String idNumber){
        this.idNumber = idNumber;
    }

    public String getPassword() { return password;}

    public void setPassword(String password){
        this.password = password;
    }

}
