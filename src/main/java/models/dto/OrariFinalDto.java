package models.dto;

public class OrariFinalDto {
    String day;
    String timestamp;
    String salla;
    String lenda;

    public OrariFinalDto(String day, String timestamp, String salla, String lenda) {
        this.day = day;
        this.timestamp = timestamp;
        this.salla = salla;
        this.lenda = lenda;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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

}
