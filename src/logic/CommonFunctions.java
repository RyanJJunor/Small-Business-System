package logic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import logic.enums.DatabaseMessageType;
import logic.enums.ValidationErrorType;


/**
 * A class containing functions that get reused elsewhere.
 *
 * @author Ryan Junor
 */
public class CommonFunctions {


    /**
     * Takes the user to their profile page
     *
     * @param node the node that is used to find the stage that is in use
     */
    static void viewProfile(Node node) {

        FXMLLoader loader = new FXMLLoader(CommonFunctions.class.getResource("/ui/fxml/ProfileScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            uiError(e);
        }


        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.setTitle("Profile");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);


    }

    /**
     * Takes the user back to the main menu
     *
     * @param node the node that is used to find the stage that is in use
     */
    static void returnHome(Node node) {

        FXMLLoader loader = new FXMLLoader(CommonFunctions.class.getResource("/ui/fxml/MainScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            uiError(e);
        }


        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.setTitle("Main");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);


    }

    /**
     * Takes the user back to the Admin Main Menu.
     *
     * @param node the node that is used to find the stage that is in use
     */
    static void showAdminMenu(Node node) {

        FXMLLoader loader = new FXMLLoader(CommonFunctions.class.getResource("/ui/fxml/AdminMainMenu.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            uiError(e);
        }

        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.setTitle("Admin Menu");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

    }

    /**
     * Displays an error dialog for certain exceptions.
     *
     * @param e The exception to provide a error dialog for.
     */
    static void uiError(Exception e) {

        String message = "";
        if ((e.getClass().getGenericSuperclass()).toString().equals("class java.lang.RuntimeException"))
            message = "Cannot find file.\nPlease contact an administrator.";
        else if ((e.getClass().getGenericSuperclass()).toString().equals("class java.io.IOException"))
            message = "Error in file.\nPlease contact an administrator.";

        Alert info = new Alert(Alert.AlertType.ERROR, message, ButtonType.CLOSE);
        info.setHeaderText("UI ERROR");
        info.setTitle("UI ERROR");

        info.showAndWait();
    }

    /**
     * Creates a variety of error dialogs based on the parameter passed in
     *
     * @param type an Enum specifying which error message should be displayed
     */
    public static void validationError(ValidationErrorType type) {

        String message = "";

        switch (type) {
            case LOGIN:
                message = "Employee number/password is incorrect.\nPlease try again.";
                break;
            case INVALIDLOGIN:
                message = "Employee number must only consist of up to 9 digits.\nPlease try again.";
                break;
            case CUSTOMER:
                message = "Please select a valid customer.";
                break;
            case DUPLICATE:
                message = "Please remove duplicate items";
                break;
            case QUANTITY:
                message = "Please enter a valid quantity.";
                break;
            case EMPTY:
                message = "Ensure that there is at least one row and none are empty.";
                break;
            case NOITEMS:
                message = "Please add at least one item to the order.";
                break;
            case FNAME:
                message = "Please enter a valid first name.\nStarting with a capital letter and no special characters.\nMust be between 3 and 15 characters.";
                break;
            case LNAME:
                message = "Please enter a valid last name.\nStarting with a capital letter and no special characters.\nMust be between 3 and 15 characters.";
                break;
            case ENUMBER:
                message = "Please enter a valid employee number.\nMust be a number with 9 digits or less and must not start with a zero.";
                break;
            case ADMIN:
                message = "Invalid value for admin.\nPlease enter either Y or N.";
                break;
            case STOCK:
                message = "Please enter a valid stock level.";
                break;
            case EMAIL:
                message = "Please enter a valid email address.";
                break;
            case PHONE:
                message = "Please enter a valid telephone number.\nMust be between 7 and 11 characters";
                break;
            case LINE1:
                message = "Please enter a valid Line 1.\nMust be between 2 and 20 characters, including spaces.\n(No special characters)";
                break;
            case LINE2:
                message = "Please enter a valid Line 2\nMust be between 2 and 20 characters, including spaces.\n(No special characters)\nThis field is optional.";
                break;
            case CITY:
                message = "Please enter a valid City.\nMust begin with a capital letter and be between 3 and 20 characters.";
                break;
            case COUNTY:
                message = "Please enter a valid County.\nMust begin with a capital letter and be between 3 and 20 characters.\nThis field is optional.";
                break;
            case POSTCODE:
                message = "Please enter a valid Postcode.\nMust match the format [1-2 capital letters][1-3 digits][2 capital letters]";
                break;
            case NOORDER:
                message = "Please select a valid order";
                break;
            case NOREPORT:
                message = "No data found between these dates.\nPlease try another date range.";
                break;
            case NOITEM:
                message = "Please select a valid item";
                break;
            case REPORT:
                message = "Please select a report type.";
                break;
            case NOTVALID:
                message = "Password is not valid.\nPlease try again.";
                break;
            case INVALIDPASS:
                message = "New password is invalid.\nPlease enter a password with 4 - 10 characters with at least 1 " +
                        "uppercase letter and 1 number.";
                break;
            case PASSNOMATCH:
                message = "Your new passwords do not match.";
                break;
            case OLDPASS:
                message = "Old password is incorrect.\nPlease try again.";
        }
        Alert info = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        info.setHeaderText("Input Error");
        info.setTitle("Input Error");

        info.showAndWait();
    }

    /**
     * Creates a variety of success dialogs based on the parameter passed in
     *
     * @param type an Enum specifying which success message should be displayed
     */
    public static void SuccessMessage(DatabaseMessageType type) {

        String message = null;

        switch (type) {
            case USERADDED:
                message = "User successfully added!";
                break;
            case USERREMOVED:
                message = "User successfully removed!";
                break;
            case PASSWORDRESET:
                message = "Password successfully reset!";
                break;
            case PASSWORDCHANGED:
                message = "Password successfully changed!";
                break;

        }

        Alert info = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        info.setHeaderText("Success");
        info.setTitle("Success");

        info.showAndWait();
    }

    /**
     * Creates a variety of failure dialogs based on the parameter passed in
     *
     * @param type an Enum specifying which failure message should be displayed
     */
    public static void failMessage(DatabaseMessageType type) {

        String message = null;

        switch (type) {
            case USERNOTADDED:
                message = "Failed to add user.";
                break;
            case USERNOTREMOVED:
                message = "Failed to remove user.";
                break;
            case PASSWORDNOTRESET:
                message = "Password not reset.";
                break;
            case PASSWORDNOTCHANGED:
                message = "Password not changed.";

        }

        Alert info = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        info.setHeaderText("Unfortunately the action failed");
        info.setTitle("Action Failed");

        info.showAndWait();
    }

    /**
     * Displays an error when a connection cannot be made to the database.
     */
    public static void sqlError() {
        String message = "A connection to the database could not be found.\nPlease contact an administrator.";

        Alert info = new Alert(Alert.AlertType.ERROR, message, ButtonType.CLOSE);
        info.setHeaderText("Connection Error");
        info.setTitle("Error");

        info.show();
    }

}
