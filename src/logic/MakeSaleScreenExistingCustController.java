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
 * The controller for the MakeSaleScreenExistingCust FXML file
 *
 * @author Ryan Junor
 */
public class MakeSaleScreenExistingCustController {

    @FXML
    private Button backButton;
    @FXML
    private ComboBox customerCombo;

    /**
     * Returns the user to the previous screen
     */
    @FXML
    public void handleBackButton() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/MakeSaleScreen.fxml"));
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
     * Gets the customer that the sale is for and takes the user to the next screen
     */
    public void handleSubmitButton() {

        String customer;


        customer = (String) customerCombo.getSelectionModel().getSelectedItem();

        if (customer != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/MakeSaleScreenExistingCust2.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (Exception e) {
                CommonFunctions.uiError(e);
            }

            MakeSaleScreenExistingCust2Controller newController = loader.getController();

            newController.setData(customer.substring(0, customer.indexOf(" ")));


            Stage primaryStage = (Stage) backButton.getScene().getWindow();
            primaryStage.setTitle("Make Sale");
            Scene scene = new Scene(root, 1280, 720);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
        } else {
            CommonFunctions.validationError(ValidationErrorType.CUSTOMER);
        }
    }

    /**
     * Populates the customer combobox with all customers
     */
    @FXML
    public void initialize() {

        ArrayList<String> customers;

        customers = Database_Statements.getCustomers();

        customerCombo.getItems().addAll(customers);

    }
}
