module com.example.fiekorari {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.fiekorari to javafx.fxml;
    exports com.example.fiekorari;
}