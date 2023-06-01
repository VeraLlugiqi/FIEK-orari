package repository;

import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import models.dto.OrariFinalDto;
import service.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrariFinalRepository {
    public static void loadFromDatabase(ObservableList list, TableView orari_table, Tab semestri, int nrSemestrit) {
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM orarizgjedhur INNER JOIN subject ON orarizgjedhur.lenda = subject.name WHERE orarizgjedhur.availableOrariZgjedhur != 0 AND semestri = ?;");
            ps.setInt(1, nrSemestrit); // Set the semester for the first tab
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new OrariFinalDto(rs.getString(7), rs.getString(6), rs.getString(4), rs.getString(5)));
            }
            orari_table.setItems(list);
            semestri.setContent(orari_table);
            conn.close();
            ps.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
