module com.example.fiekorari {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;


    exports com.example.fiekorari;
    exports controllers;
    opens controllers;
    opens com.example.fiekorari;
    exports models;
    opens models;
}