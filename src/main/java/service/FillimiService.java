package service;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import repository.FillimiRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static service.PasswordUtil.showAlert;

public class FillimiService {
    public static String getIndeksi;

    public static void setCellTable(TableColumn columnId, TableColumn columnDay, TableColumn columnTime ){
        columnId.setCellValueFactory(new PropertyValueFactory<>("sid"));
        columnDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        columnTime.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
    }

    public static void loadFromDatabase(
            Connection conn,
            PreparedStatement ps,
            ResultSet rs,
            ObservableList list,
            TableView table_orari){
        FillimiRepository.loadFromDatabase(conn, ps, rs, list, table_orari);
    }

    public static void switchToZgjedhNjeOreWithEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            //switchTo();
        }
    }

    public static boolean indeksiNukNdodhetNeListe(
            Connection conn,
            PreparedStatement ps,
            ResultSet rs,
            String indeksi
    ){
        return FillimiRepository.indeksiNukNdodhetNeListe(conn, ps, rs, indeksi);
    }

    public static int lendetRegjistruara(
            Connection conn,
            PreparedStatement ps,
            ResultSet rs
    ){
       return FillimiRepository.lendetRegjistruara(conn, ps, rs);
    }

    public static int dyOrare(
            Connection conn,
            PreparedStatement ps,
            ResultSet rs,
            int nr
    ){
        return FillimiRepository.dyOrare(conn, ps, rs, nr);
    }

    public void switchToRegjistroOren(
            Parent root,
            Stage stage,
            Scene scene
    ){
        try {
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
            e.printStackTrace();
        }
    }

    public static boolean indeksiEmpty(String indeksi){
        if(indeksi.isEmpty()){
            showAlert("Ju lutem zgjidheni nje indeks!");
            return true;
        }
        return false;
    }

    public static void loadRegjistroOret(
            Connection conn,
            PreparedStatement ps,
            ResultSet rs,
            int lendetRegjistruara,
            int nrOreve,
            int nr,
            Scene scene,
            Parent root,
            Stage stage
    ){
        //Shiko sa lende te regjistruara i ka profesori, ashtu qe i del nje alert error kur nuk ka me lende te regjistroje
        lendetRegjistruara = FillimiService.lendetRegjistruara(conn, ps, rs);
        nrOreve = FillimiService.dyOrare(conn, ps, rs, nr);
        if(nrOreve==0) {
            if (lendetRegjistruara != 0) {
                FillimiService fillimiService = new FillimiService();
                fillimiService.switchToRegjistroOren(root, stage, scene);
            } else {
                showAlert(Translate.get("joMeLende.text"));
            }
        }else{
            showAlert(Translate.get("ligheroniDyLendeError.text"));
        }
    }
}
