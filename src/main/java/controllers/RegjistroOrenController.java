package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import service.ConnectionUtil;
import service.PasswordUtil;

import java.io.IOException;
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
    PreparedStatement ps = null;
    ResultSet rs = null;

    private FillimiController fillimiController;
    ObservableList<String> lendet;
    ObservableList<String> sallat;
    String timestamp;
    String day;
    int available;


    @FXML
    TextField zgjedhOrenIdField;
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
                    "where u.idNumber = ?;");
            ps.setString(1, UserController.loggedInUserId);
            rs = ps.executeQuery();
            while (rs.next()) {
                lendet.add(rs.getString(1));
            }
        } catch (Exception e) {

        }
        lendaCombobox.setItems(lendet);

        try {
            ps = conn.prepareStatement("Select * from class WHERE available = 0");
            rs = ps.executeQuery();
            while (rs.next()) {
                sallat.add(rs.getString(2));
            }
        } catch (Exception e) {

        }
        sallaCombobox.setItems(sallat);
    }

    @FXML
    private void zgjedhOrarin(ActionEvent event) {

        String zgjedhOrenId = zgjedhOrenIdField.getText();
        String lenda = lendaCombobox.getValue();
        String salla = sallaCombobox.getValue();

        // Validate if any field is empty
        if (zgjedhOrenId.isEmpty() && lenda.isEmpty() && salla.isEmpty()) {
            showErrorAlert("All fields are required.");
            return;
        }

        try {Connection conn = ConnectionUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * from schedule where sid = ? AND available = 0");
            statement.setString(1, zgjedhOrenId);
            ResultSet resultSet1 = statement.executeQuery();
            if (resultSet1.next()) {
                timestamp = resultSet1.getString(2);
                day = resultSet1.getString(3);
                available = 0;
            }else{
                showErrorAlert("Invalid id number.");
                return;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        try { Connection conn = ConnectionUtil.getConnection();
            available = 1;
            PreparedStatement statement = conn.prepareStatement("INSERT INTO orariZgjedhur (sid, idNumber, salla, lenda, timestamp, day, available) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setString(2, UserController.loggedInUserId);
            statement.setString(1, zgjedhOrenId);
            statement.setString(3, salla);
            statement.setString(4, lenda);
            statement.setString(5, timestamp);
            statement.setString(6, day);
            statement.setInt(7, available);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Ora u regjistrua me sukses.");
                PreparedStatement statement1 = conn.prepareStatement("UPDATE class SET available = ? WHERE classname = ?");
                statement1.setInt(1, available);
                statement1.setString(2, salla);
                statement1.executeUpdate();
            } else {
                showErrorAlert("Failed to update schedule. Please try again.");
            }
        } catch (SQLException e) {
            showErrorAlert("Failed to update schedule. Please try again.");
            e.printStackTrace();
        }

}

    }

