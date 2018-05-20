package logic;

import database.Database_Statements;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logic.enums.ValidationErrorType;

/**
 * The controller for the MakeSaleScreenNewCust FXML file
 *
 * @author Ryan Junor
 */
public class MakeSaleScreenNewCustController {

    int orderNum;
    @FXML
    private Label customerNumberLabel;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField line1Field;
    @FXML
    private TextField line2Field;
    @FXML
    private TextField cityField;
    @FXML
    private TextField countyField;
    @FXML
    private TextField postcodeField;
    @FXML
    private Button backButton;
    @FXML
    private GridPane grid;

    /**
     * Checks that the fields have been filled with valid data and then creates the user, their address if applicable
     * and then processes their order.
     */
    @FXML
    private void handleSubmitButton() {


        boolean customerValid = true;
        boolean addressExists = false;
        boolean addressValid = true;
        boolean noItems = true;

        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if (!firstName.matches("^[A-Z][a-z]{2,14}$")) {
            customerValid = false;
            CommonFunctions.validationError(ValidationErrorType.FNAME);
        }
        if (!lastName.matches("^[A-Z][a-z]{2,14}$")) {
            customerValid = false;
            CommonFunctions.validationError(ValidationErrorType.LNAME);
        }
        if (!email.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?" +
                "(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")) {
            customerValid = false;
            CommonFunctions.validationError(ValidationErrorType.EMAIL);
        }
        if (!phone.matches("^[0-9]{7,11}$")) {
            customerValid = false;
            CommonFunctions.validationError(ValidationErrorType.PHONE);
        }

        String line1 = line1Field.getText().trim();
        String line2 = line2Field.getText().trim();
        String city = cityField.getText().trim();
        String county = countyField.getText().trim();
        String postcode = postcodeField.getText().trim();

        if (!line1.equals("")) {
            addressExists = true;

            if (!line1.matches("^[\\w\\s]{2,20}$")) {
                addressValid = false;
                CommonFunctions.validationError(ValidationErrorType.LINE1);
            }
            if (!line2.matches("^\\w{2,20}$") && !line2.matches("")) {
                addressValid = false;
                CommonFunctions.validationError(ValidationErrorType.LINE2);
            }
            if (!city.matches("^[A-Z]\\D{2,19}$")) {
                addressValid = false;
                CommonFunctions.validationError(ValidationErrorType.CITY);
            }
            if (!county.matches("^[A-Z]\\D{2,19}$") && !county.equals("")) {
                addressValid = false;
                CommonFunctions.validationError(ValidationErrorType.COUNTY);

            }
            if (!postcode.matches("^[A-Z]{1,2}[0-9]{1,3}[A-Z]{2}$")) {
                addressValid = false;
                CommonFunctions.validationError(ValidationErrorType.POSTCODE);
            }
        }
        if (!addressExists && customerValid && SaleMethods.makeSale(grid)) {

            Database_Statements.createCustomerNoAddress(firstName, lastName, email, phone);

            SaleMethods.sale(customerNumberLabel.getText(), SaleMethods.totalPrice);


        } else if (addressExists && addressValid && customerValid) {

            if (line2.equals(""))
                line2 = null;

            if (county.equals(""))
                county = null;

            if (grid.getChildren().size() > 0) {
                noItems = false;
            }

            if (!noItems && SaleMethods.makeSale(grid)) {

                Database_Statements.createAddress(line1, line2, city, county, postcode);

                Database_Statements.createCustomer(firstName, lastName, email, phone, Database_Statements.getNewAddressNum());

                SaleMethods.sale(customerNumberLabel.getText(), SaleMethods.totalPrice);

                handleBackButton();

            }
        }
    }

    /**
     * Returns the user to the previous screen
     */
    @FXML
    private void handleBackButton() {

        ProductRowController.setNumber(1);
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
     * calls a method that adds another item row
     */
    @FXML
    private void addItem() {
        SaleMethods.addItem(grid);
    }

    /**
     * Sets the customer number to the next available number
     */
    @FXML
    private void initialize() {

        addItem();

        customerNumberLabel.setText(String.valueOf(Database_Statements.getNewAccountNum()));

        firstNameField.setTooltip(new Tooltip("Must begin with a capital letter and be between 3 and 16 characters." +
                "\n(No special characters)"));
        lastNameField.setTooltip(new Tooltip("Must begin with a capital letter and be between 3 and 16 characters." +
                "\n(No special characters)"));
        emailField.setTooltip(new Tooltip("Enter a valid email address"));
        phoneField.setTooltip(new Tooltip("Must contain between 7 and 11 digits"));

        line1Field.setTooltip(new Tooltip("Must be between 2 and 20 characters, including spaces." +
                "\n(No special characters)"));
        line2Field.setTooltip(new Tooltip("Must be between 2 and 20 characters, including spaces." +
                "\n(No special characters)"));
        cityField.setTooltip(new Tooltip("Must begin with a capital letter and be between 3 and 20 characters."));
        countyField.setTooltip(new Tooltip("Must begin with a capital letter and be between 3 and 20 characters."));
        postcodeField.setTooltip(new Tooltip("Must match the format [1-2 capital letters][1-3 digits][2 capital letters]"));

    }
}
