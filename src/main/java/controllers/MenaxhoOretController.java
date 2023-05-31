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

    ActionEvent actionEvent;
    public static String selectedLanguageCode = "sq";
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    String getSalla;
    String getLenda;
    String getSid;
    ObservableList lista;
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
            System.out.println(UserService.loggedInUserId);
            ps = conn.prepareStatement("Select * from orarizgjedhur where idNumber = ? AND availableOrariZgjedhur!=0");
            ps.setString(1, UserService.loggedInUserId);
            rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new MenaxhoOretDto(rs.getInt(1), rs.getString(7), rs.getString(6), rs.getString(4), rs.getString(5)));
            }
            table_menaxhoOret.setItems(lista);

        }catch(Exception e){

        }
    }

    public void fshiOren(ActionEvent event) {
        fshiOrenFunction();
    }

    public void fshiOrenWithEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            fshiOrenFunction();
        }
    }


    @FXML
    public void fshiOrenFunction() {
            String indeksi = indeksiField.getText();
            if(indeksi.isEmpty()){
                showErrorAlert(Translate.get("shkruajIndeksinAlert.text"));
                return;
            }
        try{
            //Nqs indeksi nuk ndodhet ne liste shfaq error
            conn = ConnectionUtil.getConnection();
            ps = conn.prepareStatement("SELECT oid FROM orarizgjedhur where idNumber = ? AND oid = ? and availableOrariZgjedhur=1;\n");
            ps.setString(1, UserService.loggedInUserId);
            ps.setString(2, indeksi);
            rs = ps.executeQuery();
            if(!rs.next()){
                showErrorAlert(Translate.get("indeksiJoListe.text"));
                return;
            }
        }catch(Exception e){
            showErrorAlert(Translate.get("indeksiJoListe.text"));
        }
            try{
                conn = ConnectionUtil.getConnection();
                ps = conn.prepareStatement("UPDATE orarizgjedhur SET availableOrariZgjedhur = 0 WHERE oid = ?");
                ps.setString(1, indeksi);
                ps.executeUpdate();
                //Marrim vlerat e salles the lendes
                conn = ConnectionUtil.getConnection();
                ps = conn.prepareStatement("Select * from orarizgjedhur where oid = ?");
                ps.setString(1, indeksi);
                rs = ps.executeQuery();
                while(rs.next()){
                    getSalla = rs.getString(4);
                    getLenda = rs.getString(5);
                    getSid = rs.getString(2);
                }
                //Rikthejme availability te salles
                ps = conn.prepareStatement("UPDATE schedule_class " +
                        "INNER JOIN schedule ON schedule_class.sid = schedule.sid " +
                        "INNER JOIN class ON schedule_class.cid = class.cid " +
                        "SET available = 0 " +
                        "WHERE schedule.sid = ? AND class.classname = ?");

                ps.setString(1, getSid);
                ps.setString(2, getSalla);
                ps.executeUpdate();
                //Rikthejme availability te lendes
                ps = conn.prepareStatement("UPDATE professor_subject " +
                        "INNER JOIN subject ON subject.id = professor_subject.subject_id " +
                        "INNER JOIN user ON user.uid = professor_subject.professor_id " +
                        "SET professor_subject.availableProfessorSubject = 0 " +
                        "WHERE user.idNumber = ? AND subject.name = ?;");
                ps.setString(1, UserService.loggedInUserId);
                ps.setString(2, getLenda);
                ps.executeUpdate();
                showAlert(Translate.get("regjistroAlert.text"));

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
        writeScheduleLabel.setText(Translate.get("writeScheduleLabel.text"));
    }
    public void setSelectedLanguageCode(String languageCode) {
        selectedLanguageCode = languageCode;
    }

}
