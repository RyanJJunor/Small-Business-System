package logic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import logic.enums.ValidationErrorType;

/**
 * The controller for the GenerateReportScreen FXML file
 *
 * @author Ryan Junor
 */
public class GenerateReportScreenController {

    @FXML
    private Button backButton;
    @FXML
    private ComboBox reportCombo;

    /**
     * Returns the user to the previous screen
     */
    @FXML
    private void handleBackButton() {

        CommonFunctions.returnHome(backButton);
    }

    /**
     * Takes the user to the next screen for the report type they have chosen.
     */
    @FXML
    private void handleSubmitButton() {

        String reportType;
        reportType = (String) reportCombo.getSelectionModel().getSelectedItem();

        FXMLLoader loader;
        Parent root;
        Stage primaryStage;
        Scene scene;

        if (reportType != null) {

            if (!reportType.equals("Stock")) {
                loader = new FXMLLoader(getClass().getResource("/ui/fxml/GenerateReportsScreen2.fxml"));
                root = null;
                try {
                    root = loader.load();
                } catch (Exception e) {
                    CommonFunctions.uiError(e);
                }

                primaryStage = (Stage) backButton.getScene().getWindow();

                GenerateReportScreen2Controller newController = loader.getController();
                try {
                    newController.setData(reportType, primaryStage);
                } catch (NullPointerException npe) {
                    //
                }

                scene = new Scene(root, 1280, 720);
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
            } else {

                loader = new FXMLLoader(getClass().getResource("/ui/fxml/StockReportScreen.fxml"));
                root = null;
                try {
                    root = loader.load();
                } catch (Exception e) {
                    CommonFunctions.uiError(e);
                }

                primaryStage = (Stage) backButton.getScene().getWindow();
                primaryStage.setTitle("Stock Report");
                scene = new Scene(root, 1280, 720);
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
            }


        } else {
            CommonFunctions.validationError(ValidationErrorType.REPORT);
        }
    }

}
