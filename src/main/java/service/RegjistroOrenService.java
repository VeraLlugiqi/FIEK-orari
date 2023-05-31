package service;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import repository.RegjistroOrenRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static service.PasswordUtil.showAlert;
import static service.PasswordUtil.showErrorAlert;

public class RegjistroOrenService {
    public static void loadLendetFromDatabase(ObservableList lendet, ComboBox lendaCombobox) {
        RegjistroOrenRepository.loadLendetFromDatabase(lendet, lendaCombobox);
        }

        public static void loadSallatFromDatabase(ObservableList sallat, ComboBox sallaCombobox){
        RegjistroOrenRepository.loadSallatFromDatabase(sallat, sallaCombobox);
        }
        public static boolean emptySallaLenda(String lenda, String salla){
        if (lenda.isEmpty() || salla.isEmpty()) {
            showAlert(Translate.get("login.error.emptyFields"));
            return true;
        }
        return false;
    }

    public static String[] getTimestampDay(String sallaId){
        return RegjistroOrenRepository.getTimestampDay(sallaId);
    }

    public static void regjistroOrenSukses(String lenda, String salla, String[]timestampSalla) {
        RegjistroOrenRepository.regjistroOrenSukses(lenda, salla, timestampSalla);
        }
    }
