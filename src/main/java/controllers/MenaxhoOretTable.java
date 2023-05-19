package controllers;

public class MenaxhoOretTable {
    String salla;
    String lenda;
    String timestamp;
    String day;

    public MenaxhoOretTable(String day, String timestamp, String salla, String lenda) {
        this.salla = salla;
        this.lenda = lenda;
        this.timestamp = timestamp;
        this.day = day;
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
