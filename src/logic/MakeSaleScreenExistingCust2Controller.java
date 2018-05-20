package logic;

import database.Database_Statements;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * The controller of the MakeSaleScreenExistingCust2 FXML file
 *
 * @author Ryan Junor
 */
public class MakeSaleScreenExistingCust2Controller {

    @FXML
    private Label customerNumberLabel;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneLabel;

    @FXML
    private Label line1Label;
    @FXML
    private Label line2Label;
    @FXML
    private Label cityLabel;
    @FXML
    private Label countyLabel;
    @FXML
    private Label postcodeLabel;
    @FXML
    private GridPane grid;


    /**
     * Populates the labels on the screen with the customers details
     *
     * @param customerNumber the customer number of the customer who is purchasing the order
     */
    @FXML
    void setData(String customerNumber) {

        ArrayList<String> customerDetails;

        customerDetails = Database_Statements.getCustomer(customerNumber);

        customerNumberLabel.setText(customerDetails.get(0));
        firstNameLabel.setText(customerDetails.get(1));
        lastNameLabel.setText(customerDetails.get(2));
        emailLabel.setText(customerDetails.get(3));
        phoneLabel.setText(customerDetails.get(4));


        try {
            line1Label.setText(customerDetails.get(5));
            line2Label.setText(customerDetails.get(6));
            cityLabel.setText(customerDetails.get(7));
            countyLabel.setText(customerDetails.get(8));
            postcodeLabel.setText(customerDetails.get(9));
        } catch (Exception e) {
            //means there is no address for this user
        }

        addItem();
    }

    /**
     * Calls a method that processes the sale
     */
    @FXML
    private void handleSubmitButton() {

        if (SaleMethods.makeSale(grid)) {
            SaleMethods.sale(customerNumberLabel.getText(), SaleMethods.totalPrice);
            handleBackButton();
        }
    }

    /**
     * Returns the user to the previous screen.
     */
    @FXML
    void handleBackButton() {

        FXMLLoader loader = new FXMLLoader(MakeSaleScreenExistingCust2Controller.class.getResource("/ui/fxml/MakeSaleScreenExistingCust.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            CommonFunctions.uiError(e);
        }

        Stage primaryStage = (Stage) customerNumberLabel.getScene().getWindow();
        primaryStage.setTitle("Make Sale");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        ProductRowController.setNumber(1);


    }

    /**
     * calls a method that adds another item row
     */
    @FXML
    private void addItem() {

        SaleMethods.addItem(grid);

    }


}



