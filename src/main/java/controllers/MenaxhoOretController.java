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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import models.dto.MenaxhoOretDto;
import service.ConnectionUtil;
import service.MenaxhoOretService;
import service.Translate;
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static service.PasswordUtil.showAlert;
import static service.PasswordUtil.showErrorAlert;

public class MenaxhoOretController extends SceneController implements Initializable {
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

    ActionEvent actionEvent;
    ObservableList lista;
    Connection conn;


    public void initialize(URL url, ResourceBundle resourceBundle){
        try{
            conn = ConnectionUtil.getConnection();
            lista = FXCollections.observableArrayList();
            MenaxhoOretService.setCellTable(columnIndeksi, columnDita, columnLenda, columnOra, columnSalla);
            MenaxhoOretService.loadFromDatabase(lista, table_menaxhoOret);
        }catch(Exception e){

        }
        updateTexts();
    }


    @FXML
    public void fshiOrenFunction() {
            String indeksi = indeksiField.getText();
            if(MenaxhoOretService.emptyIndeks(indeksi)){
                return;
            }
            //Nqs indeksi nuk ndodhet ne liste shfaq error
            if(MenaxhoOretService.indeksiNukNdodhetNeListe(indeksi)){
                return;
            }

              MenaxhoOretService.fshiOrenFunction(indeksi);
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


    //-----------------------------Gjuha----------------------------------
    @FXML
    TextField indeksiField;
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
    Label writeScheduleLabel;

    public static String selectedLanguageCode = "sq";

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
        writeScheduleLabel.setText(Translate.get("writeScheduleLabel.text"));
    }
    public void setSelectedLanguageCode(String languageCode) {
        selectedLanguageCode = languageCode;
    }

    public void fshiOren(){
        fshiOrenFunction();
    }
    public void fshiOrenWithEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            fshiOren();
        }
    }

}
