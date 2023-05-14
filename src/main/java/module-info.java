module com.example.fiekorari {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;


    opens com.example.fiekorari to javafx.fxml;
    exports com.example.fiekorari;
}