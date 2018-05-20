package logic;

import database.Database_Statements;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import logic.enums.ValidationErrorType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The controller of the AddUserScreen FXML file.
 *
 * @author Ryan Junor
 */
public class ChangePasswordScreenController {

    @FXML
    private Button backButton;
    @FXML
    private PasswordField oldPasswordField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmNewPasswordField;


    /**
     * Changes the screen back to the profile screen
     */
    @FXML
    private void handleBackButton() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/ProfileScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            CommonFunctions.uiError(e);
        }
        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        primaryStage.setTitle("Admin Menu");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
    }

    /**
     * Change the users password to the hash of the new one entered when the submit button is pressed
     */
    @FXML
    private void handleSubmitButton() {

        String oldPassword, newPassword, confirmNewPassword;

        oldPassword = oldPasswordField.getText().trim();
        newPassword = newPasswordField.getText().trim();
        confirmNewPassword = confirmNewPasswordField.getText().trim();

        if (newPassword.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,10}$")) {

            if (Database_Statements.logIn(Integer.toString(Main.activeUser.getEmployeeNumber()), hashPass(oldPassword)).equals("yes")) {

                if (newPassword.equals(confirmNewPassword)) {

                    Database_Statements.changePassword(Integer.toString(Main.activeUser.getEmployeeNumber()), hashPass(newPassword));

                } else {
                    CommonFunctions.validationError(ValidationErrorType.PASSNOMATCH);
                }
            } else {
                CommonFunctions.validationError(ValidationErrorType.OLDPASS);
            }
        } else {
            CommonFunctions.validationError(ValidationErrorType.INVALIDPASS);
        }
    }

    /**
     * Hashes the entered password
     *
     * @param pass the password to hash
     * @return A string containing the hashed password
     */
    private String hashPass(String pass) {

        StringBuilder sb = new StringBuilder();

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes());
            byte[] bytes = md.digest();

            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

        } catch (NoSuchAlgorithmException nSAE) {
            //
        }
        return sb.toString();
    }

    /**
     * Runs when the screen is initialised, provides tooltips for fields
     */
    @FXML
    private void initialize() {

        String message = "Password length must be between 4 and 10 and contain at least 1 uppercase letter " +
                "and 1 number";

        newPasswordField.setTooltip(new Tooltip(message));
        confirmNewPasswordField.setTooltip(new Tooltip(message));

    }
}
