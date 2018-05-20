package logic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * The controller of the MakeSaleScreen FXML file
 *
 * @author Ryan Junor
 */
public class MakeSaleScreenController {


    @FXML
    private Button backButton;

    /**
     * Takes the user back to the main screen.
     */
    @FXML
    private void handleBackButton() {
        CommonFunctions.returnHome(backButton);
    }

    /**
     * Takes the user to the screen for creating a new customer and processing a sale
     */
    @FXML
    private void handleNewButton() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/MakeSaleScreenNewCust.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            CommonFunctions.uiError(e);
        }

        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        primaryStage.setTitle("Make Sale");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

    }

    /**
     * Takes the user to the screen for processing an order for an existing customer
     */
    @FXML
    private void handleExistingButton() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/MakeSaleScreenExistingCust.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            CommonFunctions.uiError(e);
        }

        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        primaryStage.setTitle("Make Sale");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

    }
}
