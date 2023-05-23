package controllers;

import controllers.FillimiControllerInterface;
import controllers.OrariController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
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
import java.sql.SQLException;
import java.util.ResourceBundle;

import static service.PasswordUtil.showAlert;

public class FillimiController extends SceneController implements Initializable{
    ActionEvent actionEvent;
    int lendetRegjistruara;
    private Stage stage;
    private Scene scene;
    private Parent root;
    public static String selectedLanguageCode = "sq";
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private ObservableList list;

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
    Label welcomeLabel;
    @FXML
    Label longTextLabel;
    @FXML
    Label indexWriteLabel;
    @FXML
    Button pickScheduleButton;







    @FXML
    private TableView<?> table_orari;
    @FXML
    private TableColumn<?, ?> columnDay;
    @FXML
    private TableColumn<?, ?> columnTime;
    @FXML
    private TableColumn<?, ?> columnId;

    private int nrOreve;

    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            conn = ConnectionUtil.getConnection();
            list = FXCollections.observableArrayList();
            setCellTable();
            loadFromDatabase();
        }catch(Exception e){

        }
        updateTexts();
    }

    private void setCellTable(){
        columnId.setCellValueFactory(new PropertyValueFactory<>("sid"));
        columnDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        columnTime.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
    }

    private void loadFromDatabase(){
        try{
            ps = conn.prepareStatement("SELECT * FROM schedule");
            rs = ps.executeQuery();
            while(rs.next()){
                list.add(new OrariController(rs.getString(1) ,rs.getString(2), rs.getString(3)));
            }
        }catch(Exception e){

        }
        table_orari.setItems(list);
    }

    @FXML
    public void switchToClose(ActionEvent event) {

        System.exit(0);
    }

    public void switchToZgjedhNjeOre(ActionEvent event) {
        switchTo();
    }

    public void switchToZgjedhNjeOreWithEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            switchTo();
        }
    }


    @FXML
    public void switchTo() {

        String indeksi = indeksiField.getText();
        //Shiko sa lende te regjistruara i ka profesori, ashtu qe i del nje alert error kur nuk ka me lende te regjistroje
        try {
            conn = ConnectionUtil.getConnection();
            ps = conn.prepareStatement("SELECT COUNT(s.name) FROM subject s " +
                    "INNER JOIN professor_subject ps ON s.id = ps.subject_id " +
                    "INNER JOIN user u " +
                    "ON u.uid = ps.professor_id " +
                    "WHERE u.idNumber = ? AND availableProfessorSubject = 0;");

            ps.setString(1, UserController.loggedInUserId);
            rs = ps.executeQuery();
            if(rs.next()){
                lendetRegjistruara = rs.getInt(1);
            }

        }catch(Exception e){
            showAlert(Translate.get("errorNrLendeDb.text"));
            e.printStackTrace();
        }

        try{
            //Profesori mos te mund te zgjedhe dy ore ne te njejtin orar check
            ps = conn.prepareStatement("SELECT count(*) FROM orarizgjedhur WHERE idNumber = ? AND sid = ? AND availableOrariZgjedhur!=0");
            ps.setString(1, UserController.loggedInUserId);
            ps.setString(2, indeksi);
            rs = ps.executeQuery();
            while(rs.next())
            nrOreve = rs.getInt(1);

        }catch(Exception e){
            showAlert(Translate.get("verifikimierror.text"));
            e.printStackTrace();
        }

        System.out.println(lendetRegjistruara);
        if(nrOreve==0) {
            if (lendetRegjistruara != 0) {
                try {
                    conn = ConnectionUtil.getConnection();
                    ps = conn.prepareStatement("SELECT * FROM schedule WHERE sid = ?");
                    ps.setString(1, indeksi);
                    rs = ps.executeQuery();
                    //Nese ekzekutohet me sukses, kjo id ekziston dhe kalojme ne dritaren tjeter
                    FillimiService.getIndeksi = indeksi;
                    root = FXMLLoader.load(getClass().getResource("/com/example/fiekorari/regjistroOren.fxml"));
                    Stage addDialogStage = new Stage();
                    addDialogStage.setTitle(Translate.get("regjistroOren.text"));
                    addDialogStage.initModality(Modality.WINDOW_MODAL);
                    addDialogStage.initOwner(stage);
                    scene = new Scene(root);
                    addDialogStage.setScene(scene);
                    System.out.println(FillimiService.getIndeksi);
                    addDialogStage.showAndWait();

                } catch (Exception e) {
                    //Perndryshe paraqesim error mesazhin
                    showAlert(Translate.get("ideksiNukEkziton.text"));
                }
            } else {
                showAlert(Translate.get("joMeLende.text"));
            }
        }else{
            showAlert(Translate.get("ligheroniDyLendeError.text"));
        }
        System.out.println(nrOreve);


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
        longTextLabel.setText(Translate.get("fiek_orariLabel.text"));
        welcomeLabel.setText(Translate.get("welcomeLabel.text"));
        columnTime.setText(Translate.get("columnTime.text"));
        columnDay.setText(Translate.get("columnDay.text"));
        columnId.setText(Translate.get("columnId.text"));
        logoutButton.setText(Translate.get("logoutButton.text"));
        NdihmaButton.setText(Translate.get("NdihmaButton.text"));
        Oraributton.setText(Translate.get("Oraributton.text"));
        profileButton.setText(Translate.get("profileButton.text"));
        manageClassButton.setText(Translate.get("manageClassButton.text"));
        startButton.setText(Translate.get("startButton.text"));
        fiek_orariLabel.setText(Translate.get("fiek_orariLabel.text"));
        indexWriteLabel.setText(Translate.get("indexWriteLabel.text"));
        pickScheduleButton.setText(Translate.get("pickScheduleButton.text"));
    }
    public void setSelectedLanguageCode(String languageCode) {
        selectedLanguageCode = languageCode;
    }


}
