package logic;

import database.Database_Statements;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import logic.enums.ValidationErrorType;

import java.util.ArrayList;

/**
 * The controller for the first Adjust Stock Screen.
 *
 * @author Ryan Junor
 */
public class AdjustStockScreenController {

    @FXML
    private ComboBox productCombo;
    @FXML
    private Button backButton;

    /**
     * Gets the product that the user would like to change the stock of and passes the product number and name to the Adjust Stock 2 controller.
     */
    @FXML
    void handleSubmitButton() {

        String item;

        item = (String) productCombo.getSelectionModel().getSelectedItem();

        if (item != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/AdjustStockScreen2.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (Exception e) {
                CommonFunctions.uiError(e);
            }

            AdjustStockScreen2Controller newController = loader.getController();
            newController.setData(Integer.parseInt(item.substring(0, item.indexOf(" "))), item.substring(item.indexOf(" ") + 1));


            Stage primaryStage = (Stage) productCombo.getScene().getWindow();
            primaryStage.setTitle("Adjust Stock");
            Scene scene = new Scene(root, 1280, 720);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
        } else {
            CommonFunctions.validationError(ValidationErrorType.NOITEM);
        }
    }

    /**
     * Takes the user back to the main screen.
     */
    @FXML
    private void handleBackButton() {
        CommonFunctions.returnHome(backButton);
    }


    /**
     * Populates the combobox when screen is initialised
     */
    @FXML
    private void initialize() {

        ArrayList<String> products;

        products = Database_Statements.getProducts();

        productCombo.getItems().addAll(products);

    }
}
