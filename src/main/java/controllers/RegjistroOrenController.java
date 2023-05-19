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
    TextField userIdField;
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

    @FXML
    private String getLendaValue() {
        String selectedValue = lendaCombobox.getValue();
        return selectedValue;
    }

    @FXML
    private String getSallaValue() {
        String selectedValue = sallaCombobox.getValue();
        return selectedValue;
    }

    private void loadLendetFromDatabase() {
        try {
            ps = conn.prepareStatement("Select * from subject");
            rs = ps.executeQuery();
            while (rs.next()) {
                lendet.add(rs.getString(2));
            }
        } catch (Exception e) {

        }
        lendaCombobox.setValue("KNK");
        lendaCombobox.setItems(lendet);

        try {
            ps = conn.prepareStatement("Select * from class");
            rs = ps.executeQuery();
            while (rs.next()) {
                sallat.add(rs.getString(2));
            }
        } catch (Exception e) {

        }
        sallaCombobox.setValue("621");
        sallaCombobox.setItems(sallat);
    }

    @FXML
    private void zgjedhOrarin(ActionEvent event) {

        String userId = userIdField.getText();
        String zgjedhOrenId = zgjedhOrenIdField.getText();
        String lenda = lendaCombobox.getValue();
        String salla = getSallaValue();

        // Validate if any field is empty
        if (userId.isEmpty() || zgjedhOrenId.isEmpty() || lenda.isEmpty() || salla.isEmpty()) {
            showErrorAlert("All fields are required.");
            return;
        }

        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * from user where idNumber = ?");
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                showErrorAlert("Invalid idNumber.");
                return;
            }
        }catch (SQLException e) {
                e.printStackTrace();
            return;
        }

        try {Connection conn = ConnectionUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * from schedule where sid = ?");
            statement.setString(1, zgjedhOrenId);
            ResultSet resultSet1 = statement.executeQuery();
            if (!resultSet1.next()) {
                showErrorAlert("Invalid id number.");
                return;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        try {
            Connection conn = ConnectionUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * from schedule where sid = ?");
            statement.setString(1, zgjedhOrenId);
            ResultSet resultSet2 = statement.executeQuery();
            if(resultSet2.next()) {
                timestamp = resultSet2.getString(2);
                day = resultSet2.getString(3);
                available = 0;
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        try { Connection conn = ConnectionUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement("INSERT INTO orariZgjedhur (sid, idNumber, salla, lenda, timestamp, day, available) VALUES (?, ?, ?, ?, ?, ?, ?)");
             available = Integer.parseInt(userId);
            statement.setString(1, zgjedhOrenId);
            statement.setString(2,userId);
            statement.setString(3, salla);
            statement.setString(4, lenda);
            statement.setString(5, timestamp);
            statement.setString(6, day);
            statement.setInt(7, available);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                PasswordUtil.showAlert("Ora u regjistrua me sukses.");
                PreparedStatement statement1 = conn.prepareStatement("UPDATE schedule SET available = ? WHERE sid = ?");
                statement1.setInt(1, available);
                statement1.setString(2, zgjedhOrenId);
                statement1.executeUpdate();


            } else {
                showErrorAlert("Failed to update password. Please try again.");
            }
        } catch (SQLException e) {
            showErrorAlert("Failed to update password. Please try again.");
            e.printStackTrace();
        }

}

    }

