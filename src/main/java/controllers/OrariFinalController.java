package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.ConnectionUtil;
import service.FillimiService;
import service.Translate;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class OrariFinalController extends SceneController implements Initializable {
    private Scene scene;
    private Parent root;
    private Stage stage;
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
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
    Label longTextLabel;
    @FXML
    Label scheduleLabel;
    @FXML
    Tab semestriDy;
    @FXML
    Tab semestriKater;
    @FXML
    Tab semestriGjashte;

    public static String selectedLanguageCode = "sq";
    @FXML
    private TableView<?> orari_table;
    @FXML
    private TableColumn<?, ?> columnDita;

    @FXML
    private TableColumn<?, ?> columnOra;
    @FXML
    private TableColumn<?, ?> columnSalla;
    @FXML
    private TableColumn<?, ?> columnLenda;
    @FXML
    private TableColumn<?, ?> columnDita1;

    @FXML
    private TableColumn<?, ?> columnOra1;
    @FXML
    private TableColumn<?, ?> columnSalla1;
    @FXML
    private TableColumn<?, ?> columnLenda1;

    @FXML
    private TableColumn<?, ?> columnDita2;

    @FXML
    private TableColumn<?, ?> columnOra2;
    @FXML
    private TableColumn<?, ?> columnSalla2;
    @FXML
    private TableColumn<?, ?> columnLenda2;


    ActionEvent actionEvent;

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
        columnDita.setCellValueFactory(new PropertyValueFactory<>("day"));
        columnLenda.setCellValueFactory(new PropertyValueFactory<>("lenda"));
        columnOra.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        columnSalla.setCellValueFactory(new PropertyValueFactory<>("salla"));
    }

    public void loadFromDatabase(){
        try{
            System.out.println(UserController.loggedInUserId);
            ps = conn.prepareStatement("Select * from orarizgjedhur where availableOrariZgjedhur!=0;");
            rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new OrariTable(rs.getString(7), rs.getString(6), rs.getString(4), rs.getString(5)));
            }
            orari_table.setItems(lista);

        }catch(Exception e){

        }
    }
    @FXML
    public void switchToChart(){
        try {
            root = FXMLLoader.load(getClass().getResource("/com/example/fiekorari/pie.fxml"));
            Stage addDialogStage = new Stage();
            addDialogStage.setTitle(Translate.get("statistika.text"));
            addDialogStage.initModality(Modality.WINDOW_MODAL);
            addDialogStage.initOwner(stage);
            scene = new Scene(root);
            addDialogStage.setScene(scene);
            System.out.println(FillimiService.getIndeksi);
            addDialogStage.showAndWait();
        }catch(Exception e){
            e.printStackTrace();
        }
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
    public void switchToLogin() throws IOException {
        switchToLogin(actionEvent);
    }
    public void switchToNdihma() throws IOException{
        switchToNdihma(actionEvent);
    }
    public void switchToOrari() throws IOException{
        switchToOrari(actionEvent);
    }

    public void setSelectedLanguageCode(String languageCode) {
        selectedLanguageCode = languageCode;
    }

    public void updateTexts(){
        longTextLabel.setText(Translate.get("longTextLabel.text"));
        scheduleLabel.setText(Translate.get("scheduleLabel.text"));
        logoutButton.setText(Translate.get("logoutButton.text"));
        NdihmaButton.setText(Translate.get("NdihmaButton.text"));
        Oraributton.setText(Translate.get("Oraributton.text"));
        profileButton.setText(Translate.get("profileButton.text"));
        manageClassButton.setText(Translate.get("manageClassButton.text"));
        startButton.setText(Translate.get("startButton.text"));
        fiek_orariLabel.setText(Translate.get("fiek_orariLabel.text"));
        columnLenda.setText(Translate.get("columnLenda.text"));
        columnSalla.setText(Translate.get("columnSalla.text"));
        columnOra.setText(Translate.get("columnOra.text"));
        columnDita.setText(Translate.get("columnDita.text"));
        columnLenda1.setText(Translate.get("columnLenda.text"));
        columnSalla1.setText(Translate.get("columnSalla.text"));
        columnOra1.setText(Translate.get("columnOra.text"));
        columnDita1.setText(Translate.get("columnDita.text"));
        columnLenda2.setText(Translate.get("columnLenda.text"));
        columnSalla2.setText(Translate.get("columnSalla.text"));
        columnOra2.setText(Translate.get("columnOra.text"));
        columnDita2.setText(Translate.get("columnDita.text"));
        semestriDy.setText(Translate.get("semestriDy.text"));
        semestriKater.setText(Translate.get("semestriKater.text"));
        semestriGjashte.setText(Translate.get("semestriGjashte.text"));
    }

}
