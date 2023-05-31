package service;

import controllers.PieChartController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import repository.LocaleBundle;

import java.util.ResourceBundle;

import static controllers.PieChartController.selectedLanguageCode;
import static service.PasswordUtil.showAlert;

public class NdihmaService {
    public void barButton(Parent root, Scene scene, Stage stage){
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

    public void pieButton(Parent root, Scene scene, Stage stage){
        try{
            ResourceBundle bundle = LocaleBundle.bundle(selectedLanguageCode);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiekorari/pie.fxml"), bundle);
            root = loader.load();
            PieChartController pieChartController = loader.getController();
            pieChartController.setSelectedLanguageCode(selectedLanguageCode);
//            pieChartController.updateTexts();
//
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
