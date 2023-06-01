package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

public class FillimiController extends SceneController implements Initializable{

    @FXML
    private TableView<?> table_orari;
    @FXML
    private TableColumn<?, ?> columnDay;
    @FXML
    private TableColumn<?, ?> columnTime;
    @FXML
    private TableColumn<?, ?> columnId;

    ActionEvent actionEvent;
    int lendetRegjistruara;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private ObservableList list;
    private int nrOreve;
    private int nr;

    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            conn = ConnectionUtil.getConnection();
            list = FXCollections.observableArrayList();
            FillimiService.setCellTable(columnId, columnDay, columnTime);
            FillimiService.loadFromDatabase(list, table_orari);
        }catch(Exception e){
            e.printStackTrace();
        }
        updateTexts();
    }


    @FXML
    public void switchTo() {
        String indeksi = indeksiField.getText();
        FillimiService.getIndeksi = indeksi;
        //Nqs indeksi empty
        if(FillimiService.indeksiEmpty(indeksi)){
            return;
        }
        //Indeksi nuk ndodhet ne liste
        if(FillimiService.indeksiNukNdodhetNeListe(indeksi)){
            return;
        }

        FillimiService.loadRegjistroOret(lendetRegjistruara, nrOreve, nr, scene, root, stage);
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

    //------------------------------------Gjuha------------------------------

    public static String selectedLanguageCode = "sq";
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

    public void switchToZgjedhNjeOre() {
        switchTo();
    }

    public void switchToZgjedhNjeOreWithEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            switchToZgjedhNjeOre();
        }
    }

}
