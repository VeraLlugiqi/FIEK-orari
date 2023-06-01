package repository;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import service.ConnectionUtil;
import service.FillimiService;
import service.Translate;
import service.UserService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static service.PasswordUtil.showAlert;
import static service.PasswordUtil.showErrorAlert;

public class RegjistroOrenRepository {
    public static void loadLendetFromDatabase(ObservableList lendet, ComboBox lendaCombobox){
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT s.name FROM subject s " +
                    "INNER JOIN professor_subject ps ON s.id = ps.subject_id " +
                    "INNER JOIN user u " +
                    "ON u.uid = ps.professor_id " +
                    "WHERE u.idNumber = ? AND availableProfessorSubject = 0;");

            ps.setString(1, UserService.loggedInUserId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lendet.add(rs.getString(1));
            }
            conn.close();
            ps.close();
            rs.close();
        } catch (Exception e) {
            showAlert("Ka ndodhur nje gabim gjate marrjes se lendeve nga databaza");

        }
        lendaCombobox.setItems(lendet);
    }

    public static void loadSallatFromDatabase(ObservableList sallat, ComboBox sallaCombobox) {
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM schedule_class " +
                    "INNER JOIN schedule ON schedule_class.sid = schedule.sid " +
                    "INNER JOIN class ON schedule_class.cid = class.cid " +
                    "WHERE available = 0 AND schedule.sid = ?;");

            ps.setString(1, FillimiService.getIndeksi);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sallat.add(rs.getString(9));
            }
            conn.close();
            ps.close();
            rs.close();
        } catch (Exception e) {
            showAlert(Translate.get("gabimMarrjaDhenave.text"));
            e.printStackTrace();
        }
        sallaCombobox.setItems(sallat);
    }

    public static String[] getTimestampDay(String sallaId){
        String[] timestampSalla = new String[2];
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM schedule WHERE sid = ?");
            ps.setString(1, sallaId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                timestampSalla[0] = rs.getString(2);
                timestampSalla[1] = rs.getString(3);
            }
            conn.close();
            ps.close();
            rs.close();
        } catch (Exception e) {
            showAlert(Translate.get("errorDatabaze.text"));
        }
        return timestampSalla;
    }

    public static void updateSalla(String salla){
        try {
            Connection conn = ConnectionUtil.getConnection();
            showAlert(Translate.get("regjistrimSukses.text"));
            //Ora regjistrohet me sukses, update vlerat e salles dhe lendes
            PreparedStatement ps = conn.prepareStatement("UPDATE schedule_class " +
                    "INNER JOIN schedule ON schedule_class.sid = schedule.sid " +
                    "INNER JOIN class ON schedule_class.cid = class.cid " +
                    "SET available = 1 " +
                    "WHERE schedule.sid = ? AND class.classname = ?");

            ps.setString(1, FillimiService.getIndeksi);
            ps.setString(2, salla);
            ps.executeUpdate();

            conn.close();
            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void updateLenda(String lenda){
        try{
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE professor_subject " +
                    "INNER JOIN subject ON subject.id = professor_subject.subject_id " +
                    "INNER JOIN user ON user.uid = professor_subject.professor_id " +
                    "SET professor_subject.availableProfessorSubject = 1 " +
                    "WHERE user.idNumber = ? AND subject.name = ?;");
            ps.setString(1, UserService.loggedInUserId);
            ps.setString(2, lenda);
            ps.executeUpdate();

            conn.close();
            ps.close();
        } catch (SQLException e) {
            showErrorAlert(Translate.get("deshtimPerditesiOrari.text"));
            e.printStackTrace();
        }
    }

    public static void regjistroOrenSukses(String lenda, String salla, String[]timestampSalla){
        try{
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO orariZgjedhur (sid, idNumber, salla, lenda, timestamp, day, availableOrariZgjedhur) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, FillimiService.getIndeksi);
            ps.setString(2, UserService.loggedInUserId);
            ps.setString(3, salla);
            ps.setString(4, lenda);
            ps.setString(5, timestampSalla[0]);
            ps.setString(6, timestampSalla[1]);
            ps.setInt(7, 1);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                updateLenda(lenda);
                updateSalla(salla);
            }else {
                showErrorAlert(Translate.get("deshtimPerditesiOrari.text"));
            }
            conn.close();
            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
