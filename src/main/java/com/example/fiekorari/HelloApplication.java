package com.example.fiekorari;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Scene;


import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fillimi.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 700, 700);
        stage.setTitle("Fillimi");
        stage.setScene(scene);
        stage.setResizable(true); // Enable window resizing
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
