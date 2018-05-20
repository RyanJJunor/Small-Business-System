package logic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * The controller of the GenerateReportScreen2 FXML file.
 *
 * @author Ryan Junor
 */
public class GenerateReportScreen2Controller {

    @FXML
    private Label title;
    @FXML
    private DatePicker datePickerStart;
    @FXML
    private DatePicker datePickerEnd;

    private String reportType;

    /**
     * Sets the title label and title of the stage depending on the report type.
     *
     * @param reportType   A string that contains the type of report to create
     * @param primaryStage The stage that is in use.
     */
    void setData(String reportType, Stage primaryStage) {
        this.reportType = reportType;

        title.setText("Generate " + reportType + " Report");

        primaryStage.setTitle(reportType + " Report");
    }

    /**
     * Takes the user to the respective screen for the selected report type
     */
    @FXML
    private void handleSubmitButton() {

        FXMLLoader loader;
        Parent root;
        Stage primaryStage;
        Scene scene;

        switch (reportType) {

            case "Orders":
                loader = new FXMLLoader(getClass().getResource("/ui/fxml/OrdersReportScreen.fxml"));
                root = null;
                try {
                    root = loader.load();
                } catch (Exception e) {
                    CommonFunctions.uiError(e);
                }

                OrdersReportScreenController oRSController = loader.getController();
                oRSController.setData(datePickerStart.getValue(), datePickerEnd.getValue());

                primaryStage = (Stage) datePickerStart.getScene().getWindow();
                primaryStage.setTitle("Orders Report");
                scene = new Scene(root, 1280, 720);
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
                break;

            case "Sales":
                loader = new FXMLLoader(getClass().getResource("/ui/fxml/SalesReportScreen.fxml"));
                root = null;
                try {
                    root = loader.load();
                } catch (Exception e) {
                    CommonFunctions.uiError(e);
                }

                SalesReportScreenController sRSController = loader.getController();
                sRSController.setData(datePickerStart.getValue(), datePickerEnd.getValue());

                primaryStage = (Stage) datePickerStart.getScene().getWindow();
                primaryStage.setTitle("Sales Report");
                scene = new Scene(root, 1280, 720);
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);

        }

    }

    /**
     * Takes the user back initial generate report screen.
     */
    @FXML
    private void handleBackButton() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/GenerateReportScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            CommonFunctions.uiError(e);
        }

        Stage primaryStage = (Stage) datePickerStart.getScene().getWindow();
        primaryStage.setTitle("Generate A Report");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

    }


}
