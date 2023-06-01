package service;

import controllers.PieChartController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ResourceBundle;
import static controllers.PieChartController.selectedLanguageCode;
import static service.PasswordUtil.showAlert;

public class NdihmaService {
    Parent root; Scene scene; Stage stage;
    public void barButton(){
        try{
            root = FXMLLoader.load(getClass().getResource("/com/example/fiekorari/barChart.fxml"));
            Stage addDialogStage = new Stage();
            addDialogStage.setTitle("BarChart");
            addDialogStage.initModality(Modality.WINDOW_MODAL);
            addDialogStage.initOwner(stage);
            scene = new Scene(root);
            addDialogStage.setScene(scene);
            System.out.println(FillimiService.getIndeksi);
            addDialogStage.showAndWait();
        }catch(Exception e){
            showAlert("Ka ndodhur nje gabim gjate marrjes se te dhenave");
            e.printStackTrace();
        }
    }

    public void pieButton(){
        try{
            ResourceBundle bundle = LocaleBundle.bundle(selectedLanguageCode);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/pie.fxml"), bundle);
            root = loader.load();
            PieChartController pieChartController = loader.getController();
            pieChartController.setSelectedLanguageCode(selectedLanguageCode);
            Stage addDialogStage = new Stage();
            addDialogStage.setTitle(("PieChart"));
            addDialogStage.initModality(Modality.WINDOW_MODAL);
            addDialogStage.initOwner(stage);
            scene = new Scene(root);
            addDialogStage.setScene(scene);
            System.out.println(FillimiService.getIndeksi);
            addDialogStage.showAndWait();
        }catch(Exception e){
            showAlert("Ka ndodhur nje gabim gjate marrjes se te dhenave");
            e.printStackTrace();
        }
    }
}
