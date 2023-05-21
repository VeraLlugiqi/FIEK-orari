package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.User;
import service.ConnectionUtil;
import service.Translate;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class MenaxhoOretController extends SceneController implements Initializable {
    ActionEvent actionEvent;
    public static String selectedLanguageCode = "sq";
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    ObservableList lista;
    @FXML
    Label fiek_orariLabel;
    @FXML
    Button startButton;
    @FXML
    Button manageClassButton;
    @FXML
    Button profileButton;
    @FXML
    Button Oraributton;
    @FXML
    Button NdihmaButton;
    @FXML
    Button logoutButton;
    @FXML
    Button fshiOren;
    @FXML
    Label manageClassesTextLabel;
    @FXML
    Label textMenageClassesLabel;

    @FXML
    private TableView<?> table_menaxhoOret;
    @FXML
    private TableColumn<?, ?> columnIndeksi;

    @FXML
    private TableColumn<?, ?> columnOra;
    @FXML
    private TableColumn<?, ?> columnDita;
    @FXML
    private TableColumn<?, ?> columnSalla;
    @FXML
    private TableColumn<?, ?> columnLenda;

    public void initialize(URL url, ResourceBundle resourceBundle){
        try{
            conn = ConnectionUtil.getConnection();
            lista = FXCollections.observableArrayList();
            setCellTable();
            loadFromDatabase();
        }catch(Exception e){

        }
        updateTexts();
    }

    public void setCellTable(){
        columnIndeksi.setCellValueFactory(new PropertyValueFactory<>("oid"));
        columnDita.setCellValueFactory(new PropertyValueFactory<>("day"));
        columnLenda.setCellValueFactory(new PropertyValueFactory<>("lenda"));
        columnOra.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        columnSalla.setCellValueFactory(new PropertyValueFactory<>("salla"));
    }

    public void loadFromDatabase(){
        try{
            System.out.println(UserController.loggedInUserId);
            ps = conn.prepareStatement("Select * from orarizgjedhur where idNumber = ? AND available!=0");
            ps.setString(1, UserController.loggedInUserId);
            rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new MenaxhoOretTable(rs.getInt(1), rs.getString(7), rs.getString(6), rs.getString(4), rs.getString(5)));
            }
            table_menaxhoOret.setItems(lista);

        }catch(Exception e){

        }
    }

    @FXML
    public void fshiOren(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/fiekorari/fshiOren.fxml"));

        Stage addDialogStage = new Stage();
        addDialogStage.setTitle("Fshi oren");
        addDialogStage.initModality(Modality.WINDOW_MODAL);
        addDialogStage.initOwner(stage);
        scene = new Scene(root);
        addDialogStage.setScene(scene);
        addDialogStage.showAndWait();
    }

    public void switchToFillimi() throws IOException{
        switchToFillimi(actionEvent);
    }
    public void switchToMenaxhoOret() throws IOException{
        switchToMenaxhoOret(actionEvent);
    }
    public void switchToProfili() throws IOException{
        switchToProfili(actionEvent);
    }
    public void switchToLogin() throws IOException{
        switchToLogin(actionEvent);
    }
    public void switchToNdihma() throws IOException{
        switchToNdihma(actionEvent);
    }
    public void switchToOrari() throws IOException{
        switchToOrari(actionEvent);
    }

    public void updateTexts() {

        columnSalla.setText(Translate.get("columnSalla.text"));
        fshiOren.setText(Translate.get("fshiOren.text"));
        manageClassesTextLabel.setText(Translate.get("manageClassesTextLabel.text"));
        textMenageClassesLabel.setText(Translate.get("textMenageClassesLabel.text"));
        columnIndeksi.setText(Translate.get("columnIndeksi.text"));
        columnLenda.setText(Translate.get("columnLenda.text"));
        columnDita.setText(Translate.get("columnDita.text"));
        columnOra.setText(Translate.get("columnOra.text"));
        logoutButton.setText(Translate.get("logoutButton.text"));
        NdihmaButton.setText(Translate.get("NdihmaButton.text"));
        Oraributton.setText(Translate.get("Oraributton.text"));
        profileButton.setText(Translate.get("profileButton.text"));
        manageClassButton.setText(Translate.get("manageClassButton.text"));
        startButton.setText(Translate.get("startButton.text"));
        fiek_orariLabel.setText(Translate.get("fiek_orariLabel.text"));

    }
    public void setSelectedLanguageCode(String languageCode) {
        selectedLanguageCode = languageCode;
    }

}
