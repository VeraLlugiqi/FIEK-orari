package repository;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.OrariModel;
import service.ConnectionUtil;
import service.FillimiService;
import service.Translate;
import service.UserService;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static service.PasswordUtil.showAlert;

public class FillimiRepository {
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    public static void loadFromDatabase(
            Connection conn,
            PreparedStatement ps,
            ResultSet rs,
            ObservableList list,
            TableView table_orari){
        try{
            conn = ConnectionUtil.getConnection();
            ps = conn.prepareStatement("SELECT * FROM schedule");
            rs = ps.executeQuery();
            while(rs.next()){
                list.add(new OrariModel(rs.getString(1) ,rs.getString(2), rs.getString(3)));
            }
        }catch(Exception e){

        }
        table_orari.setItems(list);
    }

    public static boolean indeksiNukNdodhetNeListe(
            Connection conn,
            PreparedStatement ps,
            ResultSet rs,
            String indeksi) {
        try {
            conn = ConnectionUtil.getConnection();
            ps = conn.prepareStatement("SELECT sid FROM schedule where schedule.sid = ?");
            ps.setString(1, indeksi);
            rs = ps.executeQuery();
            if (!rs.next()) {
                showAlert("Indeksi nuk ndodhet ne liste!");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int lendetRegjistruara(
            Connection conn,
            PreparedStatement ps,
            ResultSet rs
    ){
        int lendetRegjistruara = 0;
        try {
            conn = ConnectionUtil.getConnection();
            ps = conn.prepareStatement("SELECT COUNT(s.name) FROM subject s " +
                    "INNER JOIN professor_subject ps ON s.id = ps.subject_id " +
                    "INNER JOIN user u " +
                    "ON u.uid = ps.professor_id " +
                    "WHERE u.idNumber = ? AND availableProfessorSubject = 0;");

            ps.setString(1, UserService.loggedInUserId);
            rs = ps.executeQuery();
            if(rs.next()){
                lendetRegjistruara = rs.getInt(1);
            }

        }catch(Exception e){
            showAlert(Translate.get("errorNrLendeDb.text"));
            e.printStackTrace();
        }
        return lendetRegjistruara;
    }

    public static int dyOrare(
            Connection conn,
            PreparedStatement ps,
            ResultSet rs,
            int nr
    ){
        try{
            //Profesori mos te mund te zgjedhe dy ore ne te njejtin orar check
            ps = conn.prepareStatement("SELECT count(*) FROM orarizgjedhur WHERE idNumber = ? AND sid = ? AND availableOrariZgjedhur!=0");
            ps.setString(1, UserService.loggedInUserId);
            ps.setString(2, FillimiService.getIndeksi);
            rs = ps.executeQuery();
            while(rs.next())
                nr = rs.getInt(1);

        }catch(Exception e){
            showAlert(Translate.get("verifikimierror.text"));
            e.printStackTrace();
        }

        return nr;
    }

}

