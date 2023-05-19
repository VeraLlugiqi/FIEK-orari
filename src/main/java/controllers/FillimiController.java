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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.ConnectionUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FillimiController implements Initializable, FillimiControllerInterface {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private ObservableList list;


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
            ps = conn.prepareStatement("SELECT * FROM schedule WHERE available = 0");
            rs = ps.executeQuery();
            while(rs.next()){
                list.add(new OrariController(rs.getString(1) ,rs.getString(2), rs.getString(3)));
            }
        }catch(Exception e){

        }
        table_orari.setItems(list);
    }

    public void zgjedhOren(){

    }
    public void switchToLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/fiekorari/logIn.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Kyçu");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void switchToFillimi(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/fiekorari/fillimi.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Fillimi");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchToMenaxhoOret(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/fiekorari/nebaxhoOret.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Menaxho Orët");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchToProfili(ActionEvent event) throws IOException {
        System.out.println(UserController.loggedInUserId);
        root = FXMLLoader.load(getClass().getResource("/com/example/fiekorari/profili.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Profili");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToZgjedhNjeOre(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/com/example/fiekorari/regjistroOren.fxml"));

        Stage addDialogStage = new Stage();
        addDialogStage.setTitle("Regjistro oren");
        addDialogStage.initModality(Modality.WINDOW_MODAL);
        addDialogStage.initOwner(stage);
        scene = new Scene(root);
        addDialogStage.setScene(scene);
        addDialogStage.showAndWait();
    }
    @FXML
    private void switchToNdihma(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ndihma.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Ndihma");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void switchToOrari(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("orari.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Orari");
        stage.setScene(scene);
        stage.show();
    }
}
