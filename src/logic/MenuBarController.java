package logic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.stage.Screen;
import javafx.stage.Stage;

import static logic.CommonFunctions.uiError;

/**
 * The controller of the MenuBar FXML file.
 *
 * @author Ryan Junor
 */
public class MenuBarController {

    public String user = String.valueOf(Main.activeUser.getEmployeeNumber());
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu menu;

    /**
     * Empties the activeActiveUser object and logs the user out when the log out menu item is pressed
     */
    @FXML
    private void handleLogOutButton() {
        FXMLLoader loader = new FXMLLoader(CommonFunctions.class.getResource("/ui/fxml/Welcome.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            uiError(e);
        }

        Main.activeUser = null;

        Stage primaryStage = (Stage) menuBar.getScene().getWindow();
        primaryStage.setTitle("Log In");
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }


    /**
     * Takes the user to their profile page
     */
    @FXML
    private void handleProfileButton() {
        CommonFunctions.viewProfile(menuBar);
    }

    /**
     * Sets the label of the menu bar to the employee number of the active user
     */
    @FXML
    public void initialize() {

        menu.setText(user);
    }

}
