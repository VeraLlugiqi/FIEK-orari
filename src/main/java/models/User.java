package models;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String password;
    private String salt;

    public User(int id, String firstName, String lastName,String idNumber, String password, String salt){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.password = password;
        this.salt = salt;
    }

    public User(){}

    public int getId() { return id;}

    public void setId(int id){this.id = id;}

    public String getFirstName(){ return firstName;}

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName(){return lastName;}

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdNumber() { return idNumber;}

    public void setIdNumber(String idNumber){
        this.idNumber = idNumber;
    }

    public String getPassword() { return password;}

    public void setPassword(String password){
        this.password = password;
    }

    public String getSalt() { return salt;}

    public void setSalt(String salt){
        this.salt = salt;
    }


}
