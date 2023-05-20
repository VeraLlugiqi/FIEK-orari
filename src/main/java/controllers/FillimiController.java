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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.ConnectionUtil;
import service.FillimiService;

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
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private ObservableList list;

    @FXML
    TextField indeksiField;
    @FXML
    private TableView<?> table_orari;
    @FXML
    private TableColumn<?, ?> columnDay;
    @FXML
    private TableColumn<?, ?> columnTime;
    @FXML
    private TableColumn<?, ?> columnId;

    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            conn = ConnectionUtil.getConnection();
            list = FXCollections.observableArrayList();
            setCellTable();
            loadFromDatabase();
        }catch(Exception e){

        }
    }

    private void setCellTable(){
        columnId.setCellValueFactory(new PropertyValueFactory<>("sid"));
        columnDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        columnTime.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
    }

    private void loadFromDatabase(){
        try{
            ps = conn.prepareStatement("SELECT * FROM schedule WHERE availableSchedule = 0");
            rs = ps.executeQuery();
            while(rs.next()){
                list.add(new OrariController(rs.getString(1) ,rs.getString(2), rs.getString(3)));
            }
        }catch(Exception e){

        }
        table_orari.setItems(list);
    }


    @FXML
    public void switchToZgjedhNjeOre(ActionEvent event) throws IOException{

        String indeksi = indeksiField.getText();
        //Shiko sa lende te regjistruara i ka profesori, ashtu qe i del nje alert error kur nuk ka me lende te regjistroje
        try {
            conn = ConnectionUtil.getConnection();
            ps = conn.prepareStatement("select count(s.name) from subject s\n" +
                    "                    inner join professor_subject ps on s.id = ps.subject_id\n" +
                    "                    inner join user u\n" +
                    "                    on u.uid = ps.professor_id\n" +
                    "                    where u.idNumber = ? AND availableSubject = 0;");
            ps.setString(1, UserController.loggedInUserId);
            rs = ps.executeQuery();
            if(rs.next()){
                lendetRegjistruara = rs.getInt(1);
            }

        }catch(Exception e){
            showAlert("Ka ndodhur nje gabim gjate marrjes se numrit te lendeve te regjistruara");
            e.printStackTrace();
        }
        System.out.println(lendetRegjistruara);
        if(lendetRegjistruara !=0 ) {
            try {
                conn = ConnectionUtil.getConnection();
                ps = conn.prepareStatement("SELECT * FROM schedule WHERE sid = ?");
                ps.setString(1, indeksi);
                rs = ps.executeQuery();
                //Nese ekzekutohet me sukses, kjo id ekziston dhe kalojme ne dritaren tjeter
                FillimiService.getIndeksi = indeksi;
                root = FXMLLoader.load(getClass().getResource("/com/example/fiekorari/regjistroOren.fxml"));
                Stage addDialogStage = new Stage();
                addDialogStage.setTitle("Regjistro oren");
                addDialogStage.initModality(Modality.WINDOW_MODAL);
                addDialogStage.initOwner(stage);
                scene = new Scene(root);
                addDialogStage.setScene(scene);
                System.out.println(FillimiService.getIndeksi);
                addDialogStage.showAndWait();
            } catch (Exception e) {
                //Perndryshe paraqesim error mesazhin
                showAlert("Indeksi nuk ekziston!");
            }
        }else{
            showAlert("Nuk keni me lende per te shtuar!");
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

}
