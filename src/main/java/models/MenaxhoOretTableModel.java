package models;

public class MenaxhoOretTableModel {
    int oid;
    String salla;
    String lenda;
    String timestamp;
    String day;

    public MenaxhoOretTableModel(int oid, String day, String timestamp, String salla, String lenda) {
        this.oid = oid;
        this.salla = salla;
        this.lenda = lenda;
        this.timestamp = timestamp;
        this.day = day;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
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
}
