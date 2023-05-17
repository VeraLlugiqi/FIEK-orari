package com.example.fiekorari;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
<<<<<<< Updated upstream
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fillimi.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 400);
        stage.setTitle("FIEK - ORARI");

=======
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);
        stage.setTitle("Fillimi");
>>>>>>> Stashed changes
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }
}