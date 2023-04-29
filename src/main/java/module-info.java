module com.example.fiekorari {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fiekorari to javafx.fxml;
    exports com.example.fiekorari;
}