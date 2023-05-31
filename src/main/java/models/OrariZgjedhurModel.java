package models;

public class OrariZgjedhurModel {
    int oid;
    String sid;
    String idNumber;
    String salla;
    String lenda;
    String timestamp;
    String day;
    int availableOrariZgjedhur;

    public OrariZgjedhurModel(int oid, String sid, String idNumber, String salla, String lenda, String timestamp, String day, int availableOrariZgjedhur) {
        this.oid = oid;
        this.sid = sid;
        this.idNumber = idNumber;
        this.salla = salla;
        this.lenda = lenda;
        this.timestamp = timestamp;
        this.day = day;
        this.availableOrariZgjedhur = availableOrariZgjedhur;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getSalla() {
        return salla;
    }

    public void setSalla(String salla) {
        this.salla = salla;
    }

    public String getLenda() {
        return lenda;
    }

    public void setLenda(String lenda) {
        this.lenda = lenda;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getAvailableOrariZgjedhur() {
        return availableOrariZgjedhur;
    }

    public void setAvailableOrariZgjedhur(int availableOrariZgjedhur) {
        this.availableOrariZgjedhur = availableOrariZgjedhur;
    }
}
