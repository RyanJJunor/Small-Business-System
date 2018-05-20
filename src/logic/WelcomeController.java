package logic;

import database.Database_Statements;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import logic.enums.ValidationErrorType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The controller of the Welcome FXML file.
 *
 * @author Ryan Junor
 */
public class WelcomeController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button cancelButton;

    /**
     * Attempts to log a user in by comparing the entered username and password's hash against the database.
     * If successful they are logged in and taken to the main menu, if unsuccessful they will be alerted that the username and/or password are incorrect.
     */
    @FXML
    private void handleLogInButton() {

        String password = passwordField.getText();
        StringBuilder sb = new StringBuilder();

        if (usernameField.getText().matches("^[1-9][0-9]{0,8}$")) {
            String valid;

            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(password.getBytes());
                byte[] bytes = md.digest();

                for (byte aByte : bytes) {
                    sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
                }

            } catch (NoSuchAlgorithmException nSAE) {
                //
            }

            valid = Database_Statements.logIn(usernameField.getText(), sb.toString());

            if (valid.equals("yes")) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/MainScreen.fxml"));
                Parent root = null;

                try {
                    root = loader.load();
                } catch (Exception e) {
                    CommonFunctions.uiError(e);
                }

                Stage primaryStage = (Stage) usernameField.getScene().getWindow();
                primaryStage.setTitle("Main Menu");
                Scene scene = new Scene(root, 1280, 720);
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);


                primaryStage.show();

            } else if (valid.equals("no"))
                CommonFunctions.validationError(ValidationErrorType.LOGIN);
        } else {
            CommonFunctions.validationError(ValidationErrorType.INVALIDLOGIN);
        }
    }


    /**
     * Closes the program when the window is closed
     */
    @FXML
    private void handleCancelButton() {

        Stage stage = (Stage) cancelButton.getScene().getWindow();

        stage.close();


    }
}
