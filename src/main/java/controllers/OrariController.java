package controllers;

public class OrariController {
    String sid;
    String timestamp;
    String day;

    public OrariController(String sid, String timestamp, String day) {
        this.sid = sid;
        this.timestamp = timestamp;
        this.day = day;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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
