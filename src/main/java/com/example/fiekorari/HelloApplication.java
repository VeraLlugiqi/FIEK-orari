package com.example.fiekorari;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//<<<<<<< Updated upstream
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fillimi.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 400);
        stage.setTitle("Fillimi");
//=======
        class helloApplication extends Application {
            @Override
            public void start(Stage stage) throws IOException {
                FXMLLoader fxmlLoader = new FXMLLoader(com.example.fiekorari.HelloApplication.class.getResource("fillimi.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 900, 400);
                stage.setTitle("FIEK - ORARI");
//>>>>>>> Stashed changes
                stage.setScene(scene);
                stage.show();
            }

            public static void main(String[] args) {
                launch();

            }
        }
    }
}