package logic;

import database.Database_Manager;
import entities.Active_User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * The driver class of the application.
 *
 * @author Ryan Junor
 */

public class Main extends Application {

    public static Active_User activeUser = null;
    public static DecimalFormat dcf = new DecimalFormat("£#0.00");
    public static SimpleDateFormat sdt = new SimpleDateFormat("dd-MM-yyyy");
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private Database_Manager dbm = new Database_Manager();

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Begins the application
     *
     * @param primaryStage the initial stage
     */
    @Override
    public void start(Stage primaryStage) {

        dbm.startup();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/Welcome.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            CommonFunctions.uiError(e);
        }
        primaryStage.setTitle("Log In");
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    /**
     * Runs when the application is closed
     * Shuts down the database
     */
    @Override
    public void stop() {
        dbm.shutdown();
    }
}
