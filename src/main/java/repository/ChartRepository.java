package repository;

import javafx.scene.chart.XYChart;
import service.ConnectionUtil;
import service.Translate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static service.PasswordUtil.showAlert;

public class ChartRepository {

    public static void getSallaNumber(String[] sallaArray, int[] numberOfUsage) {
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT salla, COUNT(*) AS count " +
                    "FROM orarizgjedhur WHERE availableOrariZgjedhur = 1 " +
                    "GROUP BY salla " +
                    "ORDER BY count DESC " +
                    "LIMIT 3");
            ResultSet rs = ps.executeQuery();
            int index = 0;
            while (rs.next()) {
                String salla = rs.getString("salla");
                int count = rs.getInt("count");
                sallaArray[index] = salla;
                numberOfUsage[index] = count;
                index++;
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            showAlert(Translate.get("gabimMarrjaDhenave.text"));

        }

    }

    public static void getBarChartInfo(XYChart.Series<String, Integer> series){
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT day, COUNT(*) AS booked_schedules FROM orarizgjedhur WHERE availableOrariZgjedhur = 1 GROUP BY day ");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String day = rs.getString("day");
                int count = rs.getInt("booked_schedules");
                series.getData().add(new XYChart.Data<>(day, count));
            }
            conn.close();
            ps.close();
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

