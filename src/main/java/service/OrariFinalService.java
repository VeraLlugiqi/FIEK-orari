package service;

import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import repository.OrariFinalRepository;
public class OrariFinalService {
    public static void setCellTable(
            TableColumn columnDita,
            TableColumn columnLenda,
            TableColumn columnOra,
            TableColumn columnSalla
            ){
        columnDita.setCellValueFactory(new PropertyValueFactory<>("day"));
        columnLenda.setCellValueFactory(new PropertyValueFactory<>("lenda"));
        columnOra.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        columnSalla.setCellValueFactory(new PropertyValueFactory<>("salla"));
    }

    public static void loadFromDatabase(ObservableList list, TableView orari_table, Tab semestri, int nrSemestrit){
        OrariFinalRepository.loadFromDatabase(list, orari_table, semestri, nrSemestrit);
    }
}
