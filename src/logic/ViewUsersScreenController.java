package logic;

import database.Database_Statements;
import entities.User_Details;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.enums.ValidationErrorType;

import java.util.ArrayList;

public class ViewUsersScreenController {

    @FXML
    private Button backButton;
    @FXML
    private Label reportLabel;

    @FXML
    private TableView<User_Details> usersTable;

    @FXML
    private TableColumn<User_Details, Integer> employeeIdCol;
    @FXML
    private TableColumn<User_Details, String> employeeFNameCol;
    @FXML
    private TableColumn<User_Details, String> employeeLNameCol;
    @FXML
    private TableColumn<User_Details, Boolean> employeeIsAdminCol;


    /**
     * Initialises and populates the table with all employees in the database
     */
    @FXML
    private void initialize() {

        ArrayList<User_Details> users;
        users = Database_Statements.getUsers();

        if (users.isEmpty())
            CommonFunctions.validationError(ValidationErrorType.NOREPORT);

        reportLabel.setText("Current Employees in Database");

        employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeNumber"));
        employeeFNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        employeeLNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        employeeIsAdminCol.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));

        usersTable.getColumns().clear();
        usersTable.getColumns().addAll(employeeIdCol, employeeFNameCol, employeeLNameCol, employeeIsAdminCol);

        int numOfRows = 0;

        for (User_Details employee : users) {
            usersTable.getItems().add(employee);
            numOfRows++;
        }

        if (numOfRows > 12) {
            usersTable.setPrefWidth(1015);
        }

    }

    /**
     * Returns the user to the admin main menu
     */
    @FXML
    private void handleBackButton() {
        CommonFunctions.showAdminMenu(backButton);
    }
}
