package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.dto.OrariFinalDto;
import service.SceneService;
import service.Translate;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrariFinalService extends SceneService implements Initializable {
    @FXML
    private TableView<OrariFinalDto> orari_table1;
    @FXML
    private TableView<OrariFinalDto> orari_table2;
    @FXML
    private TableView<OrariFinalDto> orari_table3;
    @FXML
    private TableColumn<OrariFinalDto, String> columnDita;
    @FXML
    private TableColumn<OrariFinalDto, String> columnOra;
    @FXML
    private TableColumn<OrariFinalDto, String> columnSalla;
    @FXML
    private TableColumn<OrariFinalDto, String> columnLenda;
    @FXML
    private TableColumn<OrariFinalDto, String> columnDita1;
    @FXML
    private TableColumn<OrariFinalDto, String> columnOra1;
    @FXML
    private TableColumn<OrariFinalDto, String> columnSalla1;
    @FXML
    private TableColumn<OrariFinalDto, String> columnLenda1;
    @FXML
    private TableColumn<OrariFinalDto, String> columnDita2;
    @FXML
    private TableColumn<OrariFinalDto, String> columnOra2;
    @FXML
    private TableColumn<OrariFinalDto, String> columnSalla2;
    @FXML
    private TableColumn<OrariFinalDto, String> columnLenda2;
    @FXML
    TabPane tabPane;
    @FXML
    Tab semestriDy;
    @FXML
    Tab semestriKater;
    @FXML
    Tab semestriGjashte;
    ObservableList lista1;
    ObservableList lista2;
    ObservableList lista3;
    ActionEvent actionEvent;

    public void initialize(URL url, ResourceBundle resourceBundle){
        try{
            lista1 = FXCollections.observableArrayList();
            lista2 = FXCollections.observableArrayList();
            lista3= FXCollections.observableArrayList();
            service.OrariFinalService.setCellTable(columnDita, columnLenda, columnOra, columnSalla);
            service.OrariFinalService.setCellTable(columnDita1, columnLenda1, columnOra1, columnSalla1);
            service.OrariFinalService.setCellTable(columnDita2, columnLenda2, columnOra2, columnSalla2);
            service.OrariFinalService.loadFromDatabase(lista1, orari_table1, semestriDy, 2);
            service.OrariFinalService.loadFromDatabase(lista2, orari_table2, semestriKater, 4);
            service.OrariFinalService.loadFromDatabase(lista3, orari_table3, semestriGjashte, 6);
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
