package logic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * The controller of the AddUserScreen FXML file.
 *
 * @author Ryan Junor
 */
public class MainMenuController {

    @FXML
    private Button adminButton;

    /**
     * A method for taking the user to the admin main menu
     */
    @FXML
    private void handleAdminButton() {
        CommonFunctions.showAdminMenu(adminButton);
    }

    /**
     * Takes the user to the adjust stock screen.
     */
    @FXML
    private void handleAdjustStockButton() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/AdjustStockScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            CommonFunctions.uiError(e);
        }

        Stage primaryStage = (Stage) adminButton.getScene().getWindow();
        primaryStage.setTitle("Adjust Stock");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
    }

    /**
     * Takes the user to the make a sale screen.
     */
    @FXML
    private void handleMakeSaleButton() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/MakeSaleScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            CommonFunctions.uiError(e);
        }

        Stage primaryStage = (Stage) adminButton.getScene().getWindow();
        primaryStage.setTitle("Make Sale");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
    }

    /**
     * Takes the user to the generate report screen.
     */
    @FXML
    private void handleReportButton() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/GenerateReportScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            CommonFunctions.uiError(e);
        }

        Stage primaryStage = (Stage) adminButton.getScene().getWindow();
        primaryStage.setTitle("Generate A Report");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
    }

    /**
     * Takes the user to the produce invoice screen.
     */
    @FXML
    private void handleProduceInvoiceButton() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/ProduceInvoiceScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            CommonFunctions.uiError(e);
        }

        Stage primaryStage = (Stage) adminButton.getScene().getWindow();
        primaryStage.setTitle("Produce Invoice");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
    }

    /**
     * Makes the admin button visible if the active user is an admin
     */
    @FXML
    private void initialize() {

        if (Main.activeUser.getIsAdmin()) {

            adminButton.setVisible(true);
        } else {
            adminButton.setVisible(false);
        }
    }


}
