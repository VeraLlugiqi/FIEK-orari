package repository;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import models.dto.MenaxhoOretDto;
import service.ConnectionUtil;
import service.Translate;
import service.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static service.PasswordUtil.showAlert;
import static service.PasswordUtil.showErrorAlert;

public class MenaxhoOretRepository {
    public static void loadFromDatabase(ObservableList lista, TableView table_menaxhoOret){
        try{
            Connection conn = ConnectionUtil.getConnection();
            System.out.println(UserService.loggedInUserId);
            PreparedStatement ps = conn.prepareStatement("Select * from orarizgjedhur where idNumber = ? AND availableOrariZgjedhur!=0");
            ps.setString(1, UserService.loggedInUserId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new MenaxhoOretDto(rs.getInt(1), rs.getString(7), rs.getString(6), rs.getString(4), rs.getString(5)));
            }
            table_menaxhoOret.setItems(lista);
            conn.close();
            ps.close();
            rs.close();

        }catch(Exception e){

        }
    }

    public static boolean indeksiNukNdodhetNeListe(String indeksi){
        try{
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT oid FROM orarizgjedhur where idNumber = ? AND oid = ? and availableOrariZgjedhur=1;\n");
            ps.setString(1, UserService.loggedInUserId);
            ps.setString(2, indeksi);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                showErrorAlert("Indeksi nuk ndodhet ne liste");
                return true;
            }
            conn.close();
            ps.close();
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void updateAvailableOrariZgjedhur(String indeksi){
        try{
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE orarizgjedhur SET availableOrariZgjedhur = 0 WHERE oid = ?");
            ps.setString(1, indeksi);
            ps.executeUpdate();

            conn.close();
            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String[] getSallaLendaSid(String indeksi){
        String[] getSallaLendaSid = new String[3];
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("Select * from orarizgjedhur where oid = ?");
            ps.setString(1, indeksi);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                getSallaLendaSid[0] = rs.getString(4);
                getSallaLendaSid[1] = rs.getString(5);
                getSallaLendaSid[2] = rs.getString(2);
            }
            conn.close();
            ps.close();
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return getSallaLendaSid;
    }

    public static void updateSalla(String sid, String salla){
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE schedule_class " +
                    "INNER JOIN schedule ON schedule_class.sid = schedule.sid " +
                    "INNER JOIN class ON schedule_class.cid = class.cid " +
                    "SET available = 0 " +
                    "WHERE schedule.sid = ? AND class.classname = ?");

            ps.setString(1, sid);
            ps.setString(2, salla);
            ps.executeUpdate();

            conn.close();
            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void updateLenda(String lenda){
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE professor_subject " +
                    "INNER JOIN subject ON subject.id = professor_subject.subject_id " +
                    "INNER JOIN user ON user.uid = professor_subject.professor_id " +
                    "SET professor_subject.availableProfessorSubject = 0 " +
                    "WHERE user.idNumber = ? AND subject.name = ?;");
            ps.setString(1, UserService.loggedInUserId);
            ps.setString(2, lenda);
            ps.executeUpdate();

            conn.close();
            ps.close();
            showAlert(Translate.get("regjistroAlert.text"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
