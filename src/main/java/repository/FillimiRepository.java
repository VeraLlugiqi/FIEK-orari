package repository;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import models.dto.OrariFillimiDto;
import service.ConnectionUtil;
import service.FillimiService;
import service.Translate;
import service.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static service.PasswordUtil.showAlert;

public class FillimiRepository {
    public static void loadFromDatabase(ObservableList list, TableView table_orari){
        try{
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM schedule");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new OrariFillimiDto(rs.getString(1) ,rs.getString(2), rs.getString(3)));
            }
            conn.close();
            ps.close();
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        table_orari.setItems(list);
    }

    public static boolean indeksiNukNdodhetNeListe(String indeksi) {
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT sid FROM schedule where schedule.sid = ?");
            ps.setString(1, indeksi);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                showAlert("Indeksi nuk ndodhet ne liste!");
                return true;
            }
            conn.close();
            ps.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int lendetRegjistruara(){
        int lendetRegjistruara = 0;
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(s.name) FROM subject s " +
                    "INNER JOIN professor_subject ps ON s.id = ps.subject_id " +
                    "INNER JOIN user u " +
                    "ON u.uid = ps.professor_id " +
                    "WHERE u.idNumber = ? AND availableProfessorSubject = 0;");

            ps.setString(1, UserService.loggedInUserId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                lendetRegjistruara = rs.getInt(1);
            }
            conn.close();
            ps.close();
            rs.close();
        }catch(Exception e){
            showAlert(Translate.get("errorNrLendeDb.text"));
            e.printStackTrace();
        }
        return lendetRegjistruara;
    }

    public static int dyOrare(int nr){
        try{
            Connection conn = ConnectionUtil.getConnection();
            //Profesori mos te mund te zgjedhe dy ore ne te njejtin orar check
            PreparedStatement ps = conn.prepareStatement("SELECT count(*) FROM orarizgjedhur WHERE idNumber = ? AND sid = ? AND availableOrariZgjedhur!=0");
            ps.setString(1, UserService.loggedInUserId);
            ps.setString(2, FillimiService.getIndeksi);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                nr = rs.getInt(1);

            conn.close();
            ps.close();
            rs.close();
        }catch(Exception e){
            showAlert(Translate.get("verifikimierror.text"));
            e.printStackTrace();
        }
        return nr;
    }

}

