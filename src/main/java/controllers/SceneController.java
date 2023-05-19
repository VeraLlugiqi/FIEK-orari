package controllers;


import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.ConnectionUtil;
import service.PasswordUtil;

public class SceneController {

    private Stage stage;
    private Scene scene;
    private  Parent root;

    @FXML
    private void switchToFillimi(ActionEvent event) throws IOException {
         root = FXMLLoader.load(getClass().getResource("fillimi.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Fillimi");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchToMenaxhoOret(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("menaxhoOret.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Menaxho Orët");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchToProfili(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("profili.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Profili");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchToLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("logIn.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Kyçu");
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    private void switchToNdihma(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ndihma.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Ndihma");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToZgjedhNjeOre(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/com/example/fiekorari/regjistroOren.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Ndihma");
        stage.setScene(scene);
        stage.show();
    }
    }
