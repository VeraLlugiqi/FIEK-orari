package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import repository.RegjistroOrenRepository;
import service.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static service.PasswordUtil.showAlert;
import static service.PasswordUtil.showErrorAlert;

public class RegjistroOrenController implements Initializable {
    Connection conn = null;

    ObservableList<String> lendet;
    ObservableList<String> sallat;

    public static String selectedLanguageCode = "sq";

    @FXML
    ComboBox<String> lendaCombobox;
    @FXML
    ComboBox<String> sallaCombobox;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            conn = ConnectionUtil.getConnection();
            lendet = FXCollections.observableArrayList();
            sallat = FXCollections.observableArrayList();
            loadLendetFromDatabase();
            loadSallatFromDatabase();
        } catch (Exception e) {

        }
        updateTexts();
    }

    private void loadLendetFromDatabase() {
        RegjistroOrenService.loadLendetFromDatabase(lendet, lendaCombobox);
    }

    private void loadSallatFromDatabase(){
        RegjistroOrenService.loadSallatFromDatabase(sallat, sallaCombobox);
    }

    @FXML
    private void zgjedhOrarin(ActionEvent event) {
        String lenda = lendaCombobox.getValue();
        String salla = sallaCombobox.getValue();
        if(RegjistroOrenService.emptySallaLenda(lenda, salla)){
            return;
        }
        //Merr timestamp dhe day nga databaza
        String[] timestampSalla = RegjistroOrenService.getTimestampDay(FillimiService.getIndeksi);
        //Nese jane zgjedhur fushat me sukses, i vendosim te dhenat ne tabelen orarizgjedhur
        RegjistroOrenService.regjistroOrenSukses(lenda, salla, timestampSalla);
    }

    //------------------------Gjuha---------------------
    @FXML
    Label sallatLiraLabel;
    @FXML
    Label subjectLabel;
    @FXML
    Button pickScheduleButton;
    public void updateTexts() {
        pickScheduleButton.setText(Translate.get("pickScheduleButton.text"));
        sallatLiraLabel.setText(Translate.get("sallatLiraLabel.text"));
        subjectLabel.setText(Translate.get("subjectLabel.text"));
    }
    public void setSelectedLanguageCode(String languageCode) {
        selectedLanguageCode = languageCode;
    }
}

