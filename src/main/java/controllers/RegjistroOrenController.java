package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import service.ConnectionUtil;
import service.FillimiService;
import service.PasswordUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static service.PasswordUtil.showAlert;
import static service.PasswordUtil.showErrorAlert;

public class RegjistroOrenController implements Initializable {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    ObservableList<String> lendet;
    ObservableList<String> sallat;
    String timestamp;
    String day;
    int available;

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
        } catch (Exception e) {

        }
    }


    private void loadLendetFromDatabase() {
        try {
            ps = conn.prepareStatement("select s.name from subject s\n" +
                    "inner join professor_subject ps on s.id = ps.subject_id\n" +
                    "inner join user u\n" +
                    "on u.uid = ps.professor_id\n" +
                    "where u.idNumber = ? AND availableSubject = 0;");
            ps.setString(1, UserController.loggedInUserId);
            rs = ps.executeQuery();
            while (rs.next()) {
                lendet.add(rs.getString(1));
            }
        } catch (Exception e) {
            showAlert("Ka ndodhur nje gabim gjate marrjes se lendeve nga databaza");

        }
        lendaCombobox.setItems(lendet);

        try {
            ps = conn.prepareStatement("SELECT * FROM class, schedule, user WHERE sid = ? AND idNumber = ? AND availableClass = 0 ");
            ps.setString(1, FillimiService.getIndeksi);
            ps.setString(2, UserController.loggedInUserId);
            rs = ps.executeQuery();
            while (rs.next()) {
                sallat.add(rs.getString(2));
            }
        } catch (Exception e) {
            showAlert("Ka ndodhur nje gabim gjate marrjes se sallave nga databaza");
            e.printStackTrace();
        }
        sallaCombobox.setItems(sallat);
    }


    @FXML
    private void zgjedhOrarin(ActionEvent event) {
        String lenda = lendaCombobox.getValue();
        String salla = sallaCombobox.getValue();

        // Validate if any field is empty
        if (lenda.isEmpty() && salla.isEmpty()) {
            showErrorAlert("All fields are required.");
            return;
        }

        //Merr timestamp dhe day nga databaza
        try {
            conn = ConnectionUtil.getConnection();
            ps = conn.prepareStatement("SELECT * FROM schedule WHERE sid = ?");
            ps.setString(1, FillimiService.getIndeksi);
            rs = ps.executeQuery();
            if (rs.next()) {
                timestamp = rs.getString(2);
                day = rs.getString(3);
            }
        } catch (Exception e) {
            showAlert("Ka ndodhur nje gabim gjate marrjes se time dhe day nga databaza");
        }
        //Nese jane zgjedhur fushat me sukses, i vendosim te dhenat ne tabelen orarizgjedhur

            try {
                Connection conn = ConnectionUtil.getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO orariZgjedhur (sid, idNumber, salla, lenda, timestamp, day, availableOrariZgjedhur) VALUES (?, ?, ?, ?, ?, ?, ?)");
                statement.setString(1, FillimiService.getIndeksi);
                statement.setString(2, UserController.loggedInUserId);
                statement.setString(3, salla);
                statement.setString(4, lenda);
                statement.setString(5, timestamp);
                statement.setString(6, day);
                statement.setInt(7, 1);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert("Ora u regjistrua me sukses.");
                    //Ora regjistrohet me sukses, update vlerat e salles dhe lendes
                    ps = conn.prepareStatement("UPDATE class, schedule, user, subject SET availableSubject = 1, availableClass = 1, availableSchedule = 1 WHERE sid = ? AND classname = ? AND idNumber = ? AND name = ?");
                    ps.setString(1, FillimiService.getIndeksi);
                    ps.setString(2, salla);
                    ps.setString(3, UserController.loggedInUserId);
                    ps.setString(4, lenda);
                    ps.executeUpdate();
                } else {
                    showErrorAlert("Failed to update schedule. Please try again.");
                }
            } catch (SQLException e) {
                showErrorAlert("Failed to update schedule. Please try again.");
                e.printStackTrace();
            }

        }

    }

