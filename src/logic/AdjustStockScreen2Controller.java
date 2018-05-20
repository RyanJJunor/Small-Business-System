package logic;

import database.Database_Statements;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.enums.ValidationErrorType;

/**
 * The controller for the AdjustStockScreen2 FXML file.
 *
 * @author Ryan Junor
 */
public class AdjustStockScreen2Controller {

    @FXML
    private Label stockLevelLabel;
    @FXML
    private Label itemNumberLabel;
    @FXML
    private Button backButton;
    @FXML
    private TextField stockLevelField;

    private int productNumber;

    /**
     * Sets the labels on the screen when it is loaded.
     *
     * @param productNum  The product number of the product which is to have it's stock level changed.
     * @param productName The product name of the product which is to have it's stock level changed.
     */
    void setData(int productNum, String productName) {

        productNumber = productNum;
        int stockLevelNum;

        stockLevelNum = Database_Statements.getStockLevel(productNum);

        itemNumberLabel.setText("Product: " + productNum + " " + productName);
        stockLevelLabel.setText("Current Stock Level : " + stockLevelNum);
    }

    /**
     * Updates the stock level in the database and on the screen.
     */
    @FXML
    private void handleSubmitButton() {

        int newStockLevel = -1;
        boolean validStock = true;

        try {
            newStockLevel = Integer.parseInt(stockLevelField.getText());
        } catch (NumberFormatException nfe) {
            validStock = false;
        }

        if (newStockLevel < 0 || !validStock) {
            CommonFunctions.validationError(ValidationErrorType.STOCK);
        } else {
            Database_Statements.updateStock((newStockLevel), productNumber);

            int stockLevelNum = Database_Statements.getStockLevel((productNumber));

            stockLevelLabel.setText("Current Stock Level : " + stockLevelNum);
        }
    }

    /**
     * Returns the user to the first Adjust Stock Screen.
     */
    @FXML
    private void handleBackButton() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/AdjustStockScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            CommonFunctions.uiError(e);
        }


        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        primaryStage.setTitle("Adjust Stock");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

    }
}
