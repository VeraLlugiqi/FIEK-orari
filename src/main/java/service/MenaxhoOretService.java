package service;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import repository.MenaxhoOretRepository;

import static service.PasswordUtil.showErrorAlert;

public class MenaxhoOretService {
    public static void setCellTable(
            TableColumn columnIndeksi,
            TableColumn columnDita,
            TableColumn columnLenda,
            TableColumn columnOra,
            TableColumn columnSalla
    ){
        columnIndeksi.setCellValueFactory(new PropertyValueFactory<>("oid"));
        columnDita.setCellValueFactory(new PropertyValueFactory<>("day"));
        columnLenda.setCellValueFactory(new PropertyValueFactory<>("lenda"));
        columnOra.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        columnSalla.setCellValueFactory(new PropertyValueFactory<>("salla"));
    }

    public static void loadFromDatabase(ObservableList lista, TableView table_menaxhoOret){
        MenaxhoOretRepository.loadFromDatabase(lista, table_menaxhoOret);
    }

    public static boolean emptyIndeks(String indeksi){
        if(indeksi.isEmpty()){
            showErrorAlert("Empty indeks");
            return true;
        }
        return false;
    }

    public static boolean indeksiNukNdodhetNeListe(String indeksi){
        return MenaxhoOretRepository.indeksiNukNdodhetNeListe(indeksi);
    }

    public static void updateAvailableOrariZgjedhur(String indeksi){
        MenaxhoOretRepository.updateAvailableOrariZgjedhur(indeksi);
    }

    public static String[] getSallaLendaSid(String indeksi) {
        return MenaxhoOretRepository.getSallaLendaSid(indeksi);
    }

    public static void updateSalla(String sid, String salla){
        MenaxhoOretRepository.updateSalla(sid, salla);
    }
    public static void updateLenda(String lenda) {
        MenaxhoOretRepository.updateLenda(lenda);
    }

    public static void fshiOrenFunction(String indeksi){
        updateAvailableOrariZgjedhur(indeksi);
        //Marrim vlerat e salles the lendes
        String[] getSallaLendaSid = getSallaLendaSid(indeksi);
        String salla = getSallaLendaSid[0];
        String lenda = getSallaLendaSid[1];
        String sid = getSallaLendaSid[2];
        //Rikthejme availability te salles
        updateSalla(sid, salla);
        //Rikthejme availability te lendes
        updateLenda(lenda);
    }


}
