package logic;

import database.Database_Statements;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import reports.Order_Row;

import java.util.ArrayList;

/**
 * The controller for the InvoiceScreen FXML file.
 *
 * @author Ryan Junor
 */
public class InvoiceScreenController {

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

    @FXML
    private Label bottomPriceLabel;
    @FXML
    private Label orderLabel;
    @FXML
    private Label employeeLabel;

    @FXML
    private Button backButton;

    /**
     * Populates the labels on the screen with the customers details
     * @param customerNumber the customer number of the customer who is purchasing the order
     * @param orderNumber the order number
     */
    @FXML
    void setData(int customerNumber, String orderNumber) {

        ArrayList<String> customerDetails;

        customerDetails = Database_Statements.getCustomer(Integer.toString(customerNumber));

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

        ArrayList<Order_Row> order;
        order = Database_Statements.getCustomerOrder(Integer.parseInt(orderNumber));

        int count = 0;
        double rowPrice = 0;
        double totalPrice = 0;

        for (Order_Row row : order) {

            rowPrice = (row.getItemPrice() * Double.parseDouble(row.getItemQuantity()));

            InvoiceRowController irc = new InvoiceRowController(row.getItemNum() + " " + row.getItemName(), row.getItemQuantity(), rowPrice);
            grid.add(irc, 0, count);
            count++;
            totalPrice += rowPrice;

        }

        bottomPriceLabel.setText("Total Price: " + Main.dcf.format(totalPrice));
        orderLabel.setText("Order Number: " + orderNumber);
        employeeLabel.setText("Order Processed By: " + order.get(0).getEmployeeNum());
    }

    /**
     * Returns the user back to the produce invoice screen
     */
    @FXML
    private void handleBackButton() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/ProduceInvoiceScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            CommonFunctions.uiError(e);
        }

        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        primaryStage.setTitle("Produce Invoice");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
    }

    /**
     * Prints the invoice
     */
    @FXML
    private void handlePrintButton() {
        //todo
    }
}
