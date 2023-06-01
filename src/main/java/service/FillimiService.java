package service;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import repository.FillimiRepository;
import static service.PasswordUtil.showAlert;

public class FillimiService {
    public static String getIndeksi;
    Parent root; Stage stage; Scene scene;

    public static void setCellTable(TableColumn columnId, TableColumn columnDay, TableColumn columnTime ){
        columnId.setCellValueFactory(new PropertyValueFactory<>("sid"));
        columnDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        columnTime.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
    }

    public static void loadFromDatabase(ObservableList list, TableView table_orari){
        FillimiRepository.loadFromDatabase(list, table_orari);
    }
    public static boolean indeksiNukNdodhetNeListe(String indeksi){
        return FillimiRepository.indeksiNukNdodhetNeListe(indeksi);
    }
    public static int lendetRegjistruara(){
       return FillimiRepository.lendetRegjistruara();
    }

    public static int dyOrare(int nr){
        return FillimiRepository.dyOrare(nr);
    }

    public void switchToRegjistroOren(){
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
            int lendetRegjistruara,
            int nrOreve,
            int nr
    ){
        //Shiko sa lende te regjistruara i ka profesori, ashtu qe i del nje alert error kur nuk ka me lende te regjistroje
        lendetRegjistruara = FillimiService.lendetRegjistruara();
        nrOreve = FillimiService.dyOrare(nr);
        if(nrOreve==0) {
            if (lendetRegjistruara != 0) {
                FillimiService fillimiService = new FillimiService();
                fillimiService.switchToRegjistroOren();
            } else {
                showAlert(Translate.get("joMeLende.text"));
            }
        }else{
            showAlert(Translate.get("ligheroniDyLendeError.text"));
        }
    }
}
