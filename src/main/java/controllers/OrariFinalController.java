package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.dto.OrariFinalDto;
import service.ConnectionUtil;
import service.OrariFinalService;
import service.Translate;
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class OrariFinalController extends SceneController implements Initializable {
    Connection conn;
    ObservableList lista1;
    ObservableList lista2;
    ObservableList lista3;
    @FXML
    private TableView<?> orari_table1;
    @FXML
    private TableView<?> orari_table2;
    @FXML
    private TableView<?> orari_table3;
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
    @FXML
    TabPane tabPane;
    @FXML
    Tab semestriDy;
    @FXML
    Tab semestriKater;
    @FXML
    Tab semestriGjashte;
    ActionEvent actionEvent;

    public void initialize(URL url, ResourceBundle resourceBundle){
        try{
            conn = ConnectionUtil.getConnection();
            lista1 = FXCollections.observableArrayList();
            lista2 = FXCollections.observableArrayList();
            lista3= FXCollections.observableArrayList();
            OrariFinalService.setCellTable(columnDita, columnLenda, columnOra, columnSalla);
            OrariFinalService.setCellTable(columnDita1, columnLenda1, columnOra1, columnSalla1);
            OrariFinalService.setCellTable(columnDita2, columnLenda2, columnOra2, columnSalla2);
            OrariFinalService.loadFromDatabase(lista1, orari_table1, semestriDy, 2);
            OrariFinalService.loadFromDatabase(lista2, orari_table2, semestriKater, 4);
            OrariFinalService.loadFromDatabase(lista3, orari_table3, semestriGjashte, 6);
        }catch(Exception e){

        }
        updateTexts();

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

    //------------------------Gjuha-------------------
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

    public static String selectedLanguageCode = "sq";

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
