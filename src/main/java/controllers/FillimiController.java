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

public class FillimiController extends SceneController implements Initializable{
    ActionEvent actionEvent;
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
