package com.example.fiekorari;

import controllers.FillimiControllerInterface;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.Translate;

import java.io.IOException;

public class HelloApplication extends Application  {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menaxhoOret.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(Translate.get("login.text"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }



}