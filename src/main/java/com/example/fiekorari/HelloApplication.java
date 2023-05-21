package com.example.fiekorari;

import controllers.FillimiControllerInterface;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application  {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fillimi.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Fillimi");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }



}