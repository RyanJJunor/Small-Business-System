package logic;

import database.Database_Statements;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * The controller of the RemoveUserScreen FXML file.
 *
 * @author Ryan Junor
 */
public class RemoveUserScreenController {

    @FXML
    private TextField employeeNumberField;
    @FXML
    private Button backButton;

    /**
     * Return the user back to the admin main menu
     */
    @FXML
    private void handleBackButton() {

        CommonFunctions.showAdminMenu(backButton);

    }

    /**
     * Removes the user with the entered employee number from the database
     */
    @FXML
    private void handleSubmitButton() {
        Database_Statements.removeUser(employeeNumberField.getText().trim());
    }
}
