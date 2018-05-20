package logic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * The controller of the ProfileScreen FXML file.
 *
 * @author Ryan Junor
 */
public class ProfileScreenController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private Label employeeLabel;
    @FXML
    private Label adminLabel;

    /**
     * Takes the user back to the main menu screen
     */
    @FXML
    private void handleBackButton() {
        CommonFunctions.returnHome(welcomeLabel);
    }

    /**
     * Takes the user to the change password screen.
     */
    @FXML
    private void handleChangePasswordButton() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/ChangePasswordScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            CommonFunctions.uiError(e);
        }


        Stage primaryStage = (Stage) welcomeLabel.getScene().getWindow();
        primaryStage.setTitle("Change Password");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

    }

    /**
     * Sets the labels to the users information retrieved from the ActiveUser class.
     */
    @FXML
    private void initialize() {

        String admin;

        if (Main.activeUser.getIsAdmin())
            admin = "Yes";
        else
            admin = "No";

        welcomeLabel.setText("Welcome " + Main.activeUser.getFirstName() + " " + Main.activeUser.getLastName());
        employeeLabel.setText("Employee Number: " + Main.activeUser.getEmployeeNumber());
        adminLabel.setText("Admin: " + admin);
    }
}
