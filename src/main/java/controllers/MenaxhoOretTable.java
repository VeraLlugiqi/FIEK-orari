package controllers;

public class MenaxhoOretTable {
    String sid;
    String salla;
    String lenda;
    String timestamp;
    String day;

    public MenaxhoOretTable(String sid, String day, String timestamp, String salla, String lenda) {
        this.sid = sid;
        this.salla = salla;
        this.lenda = lenda;
        this.timestamp = timestamp;
        this.day = day;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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
