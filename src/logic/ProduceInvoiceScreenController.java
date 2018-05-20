package logic;

import database.Database_Statements;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import logic.enums.ValidationErrorType;

/**
 * The controller of the ProduceInvoiceScreen FXML file
 *
 * @author Ryan Junor
 */
public class ProduceInvoiceScreenController {

    @FXML
    private ComboBox<String> customerCombo;

    @FXML
    private ComboBox<String> orderCombo;

    /**
     * Takes the user to the InvoiceScreen if they select an order placed by a customer
     */
    @FXML
    private void handleSubmitButton() {

        boolean valid = true;

        String customer = customerCombo.getSelectionModel().getSelectedItem();

        if (customer != null) {
            int customerNumber = Integer.parseInt(customer.substring(0, customer.indexOf(' ')));

            String order = orderCombo.getSelectionModel().getSelectedItem();
            String orderNumber = null;

            try {
                orderNumber = order.substring(1, order.indexOf(' '));
            } catch (Exception e) {
                CommonFunctions.validationError(ValidationErrorType.NOORDER);
                valid = false;
            }


            if (valid) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/InvoiceScreen.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (Exception e) {
                    CommonFunctions.uiError(e);
                }

                InvoiceScreenController newController = loader.getController();

                newController.setData(customerNumber, orderNumber);


                Stage primaryStage = (Stage) customerCombo.getScene().getWindow();
                primaryStage.setTitle("Invoice");
                Scene scene = new Scene(root, 1280, 720);
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);

            }
        } else {
            CommonFunctions.validationError(ValidationErrorType.CUSTOMER);
        }
    }

    /**
     * Takes the user back to the main screen.
     */
    @FXML
    private void handleBackButton() {
        CommonFunctions.returnHome(customerCombo);
    }

    @FXML
    private void handleCustomerCombo() {
        orderCombo.getItems().clear();

        String customer = customerCombo.getSelectionModel().getSelectedItem();
        int customerNumber = Integer.parseInt(customer.substring(0, customer.indexOf(' ')));
        orderCombo.getItems().addAll(Database_Statements.getCustomerOrders(customerNumber));

        if (orderCombo.getItems().size() == 0)
            orderCombo.getItems().add("No orders placed");

    }

    /**
     * Populates customerCombo with all of the customers in the database.
     */
    @FXML
    private void initialize() {

        customerCombo.getItems().addAll(Database_Statements.getCustomers());
    }
}
