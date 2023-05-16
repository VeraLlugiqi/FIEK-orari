package com.example.fiekorari;


import java.io.IOException;

        import javafx.event.ActionEvent;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;

public class SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void switchToFillimi(ActionEvent event) throws IOException {
         root = FXMLLoader.load(getClass().getResource("fillimi.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Fillimi");
        stage.setScene(scene);
        stage.show();
    }

    public void switchToMenaxhoOret(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("menaxhoOret.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Menaxho Orët");
        stage.setScene(scene);
        stage.show();
    }
    public void switchToProfili(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("profili.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Profili");
        stage.setScene(scene);
        stage.show();
    }
    public void switchToLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("logIn.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Kyçu");
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSignUp(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("signUp.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Regjistrohu");
        stage.setScene(scene);
        stage.show();
    }

    public void switchBackToLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("logIn.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Kyçu");
        stage.setScene(scene);
        stage.show();
    }



}