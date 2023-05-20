package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import service.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static service.PasswordUtil.showAlert;
import static service.PasswordUtil.showErrorAlert;

public class FshiOrenController {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int available;

    @FXML
    TextField indeksiField;
    @FXML
    public void fshi(ActionEvent event){
        String indeksi = indeksiField.getText();

        if(indeksi.isEmpty()){
            showErrorAlert("Shkruani indeksin e ores!");
            return;
        }

        try{
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement statement = conn.prepareStatement("Update orarizgjedhur SET available = ? WHERE idNumber = ? AND sid = ?");
            statement.setInt(1, 1);
            statement.setString(2, UserController.loggedInUserId);
            statement.setString(3, indeksi);
            statement.executeUpdate();
            showAlert("Ora u fshi me sukses!");

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
