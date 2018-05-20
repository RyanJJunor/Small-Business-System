package logic;

import database.Database_Statements;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import logic.enums.ValidationErrorType;

/**
 * The controller of the AddUserScreen FXML file.
 *
 * @author Ryan Junor
 */
public class AddUserScreenController {

    @FXML
    private Button backButton;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField employeeNumberField;
    @FXML
    private TextField adminField;

    /**
     * Changes the screen back to the admin main menu
     */
    @FXML
    private void handleBackButton() {
        CommonFunctions.showAdminMenu(backButton);
    }

    /**
     * Adds a user to the database using the details entered when the submit button is pressed.
     */
    @FXML
    private void handleSubmitButton() {

        String newUserFName, newUserLName, newUserENumber, newUserAdmin;
        boolean validFName = false, validLName = false, validENumber = false, validAdmin = false, admin = false;

        newUserFName = firstNameField.getText().trim();
        newUserLName = lastNameField.getText().trim();
        newUserENumber = employeeNumberField.getText().trim();
        newUserAdmin = adminField.getText().trim();

        if (newUserFName.matches("^[A-Z][a-z]{2,15}$")) {
            validFName = true;
        } else
            CommonFunctions.validationError(ValidationErrorType.FNAME);

        if (newUserLName.matches("^[A-Z][a-z]{2,15}$")) {
            validLName = true;
        } else
            CommonFunctions.validationError(ValidationErrorType.LNAME);

        if (newUserENumber.matches("^[1-9][0-9]{0,8}$")) {
            validENumber = true;
        } else
            CommonFunctions.validationError(ValidationErrorType.ENUMBER);

        if (newUserAdmin.equalsIgnoreCase("Y")) {
            admin = true;
            validAdmin = true;
        } else if (newUserAdmin.equalsIgnoreCase("N")) {
            validAdmin = true;
        } else
            CommonFunctions.validationError(ValidationErrorType.ADMIN);

        if (validFName && validLName && validENumber && validAdmin) {
            Database_Statements.addUser(newUserENumber, newUserFName, newUserLName, admin);
        }
    }
}
