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
import models.OrariTableModel;
import service.ConnectionUtil;
import service.Translate;
import service.UserService;

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
    ObservableList lista1;
    ObservableList lista2;
    ObservableList lista3;
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
    TabPane tabPane;
    @FXML
    Tab semestriDy;
    @FXML
    Tab semestriKater;
    @FXML
    Tab semestriGjashte;

    public static String selectedLanguageCode = "sq";
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


    ActionEvent actionEvent;

    public void initialize(URL url, ResourceBundle resourceBundle){
        try{
            conn = ConnectionUtil.getConnection();
            lista1 = FXCollections.observableArrayList();
            lista2 = FXCollections.observableArrayList();
            lista3= FXCollections.observableArrayList();
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
        columnDita1.setCellValueFactory(new PropertyValueFactory<>("day"));
        columnLenda1.setCellValueFactory(new PropertyValueFactory<>("lenda"));
        columnOra1.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        columnSalla1.setCellValueFactory(new PropertyValueFactory<>("salla"));
        columnDita2.setCellValueFactory(new PropertyValueFactory<>("day"));
        columnLenda2.setCellValueFactory(new PropertyValueFactory<>("lenda"));
        columnOra2.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        columnSalla2.setCellValueFactory(new PropertyValueFactory<>("salla"));
    }

    public void loadFromDatabase() {
        try {
            System.out.println(UserService.loggedInUserId);
            ps = conn.prepareStatement("SELECT * FROM orarizgjedhur INNER JOIN subject ON orarizgjedhur.lenda = subject.name WHERE orarizgjedhur.availableOrariZgjedhur != 0 AND semestri = ?;");
            ps.setInt(1, 2); // Set the semester for the first tab
            rs = ps.executeQuery();
            while (rs.next()) {
                lista1.add(new OrariTableModel(rs.getString(7), rs.getString(6), rs.getString(4), rs.getString(5)));
            }
            orari_table1.setItems(lista1);
            semestriDy.setContent(orari_table1);

            ps.setInt(1, 4); // Set the semester for the second tab
            rs = ps.executeQuery();
            while (rs.next()) {
                lista2.add(new OrariTableModel(rs.getString(7), rs.getString(6), rs.getString(4), rs.getString(5)));
            }
            orari_table2.setItems(lista2);
            ps.setInt(1, 6); // Set the semester for the third tab
            rs = ps.executeQuery();
            while (rs.next()) {
                lista3.add(new OrariTableModel(rs.getString(7), rs.getString(6), rs.getString(4), rs.getString(5)));

                orari_table3.setItems(lista3);
            }
            semestriDy.setContent(orari_table1);
            semestriKater.setContent(orari_table2);
            semestriGjashte.setContent(orari_table3);
        } catch (Exception e) {
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
