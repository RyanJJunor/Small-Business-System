package logic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * The controller of the AddUserScreen FXML file.
 *
 * @author Ryan Junor
 */
public class AdminMainMenuController {

    @FXML
    private Button backButton;

    /**
     * Changes the screen back to the main menu
     */
    @FXML
    private void handleBackButton() {
        CommonFunctions.returnHome(backButton);
    }

    /**
     * Changes the screen to the add user screen
     */
    @FXML
    private void handleAddUserButton() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/AddUserScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            CommonFunctions.uiError(e);
        }

        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        primaryStage.setTitle("Add User");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
    }

    /**
     * Changes the screen to the remove user screen
     */
    @FXML
    private void handleRemoveUserButton() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/RemoveUserScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            CommonFunctions.uiError(e);
        }

        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        primaryStage.setTitle("Remove User");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
    }

    /**
     * Changes the screen to the reset password screen
     */
    @FXML
    private void handleResetPasswordButton() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/ResetPasswordScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            CommonFunctions.uiError(e);
        }

        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        primaryStage.setTitle("Reset Password");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
    }

    /**
     * Changes the screen to the view users screen
     */
    @FXML
    private void handleUsersButton() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/ViewUsersScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            CommonFunctions.uiError(e);
        }

        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        primaryStage.setTitle("Users");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
    }
}