package logic;

import database.Database_Statements;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.enums.ValidationErrorType;
import reports.Order_Details;
import reports.Order_Summary;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 * The controller of the OrdersReportScreen FXML file
 *
 * @author Ryan Junor
 */
public class OrdersReportScreenController {

    @FXML
    private Button backButton;
    @FXML
    private Label reportLabel;

    @FXML
    private TableView<Order_Details> reportTable;

    @FXML
    private TableColumn<Order_Details, Integer> orderNumCol;
    @FXML
    private TableColumn<Order_Details, Integer> customerNumCol;
    @FXML
    private TableColumn<Order_Details, Double> priceCol;
    @FXML
    private TableColumn<Order_Details, Date> orderDateCol;
    @FXML
    private TableColumn<Order_Details, Integer> employeeNumCol;


    @FXML
    private TableView<Order_Summary> summaryTable;

    @FXML
    private TableColumn<Order_Summary, String> numOfOrdersTitle;
    @FXML
    private TableColumn<Order_Summary, Integer> numOfOrdersCol;
    @FXML
    private TableColumn<Order_Summary, String> unitPriceTitle;
    @FXML
    private TableColumn<Order_Summary, String> unitPriceCol;
    @FXML
    private TableColumn<Order_Summary, String> vatPriceTitle;
    @FXML
    private TableColumn<Order_Summary, String> vatPriceCol;
    @FXML
    private TableColumn<Order_Summary, String> grossPriceTitle;
    @FXML
    private TableColumn<Order_Summary, String> grossPriceCol;

    /**
     * Initialises the report table's columns and populates them with data from Order_Details objects, initialises the
     * summary table's columns and populates them with data from Sale_Summary objects and sets the text for the
     * report label to the time frame represented in the report.
     *
     * @param startDate the start date of the report.
     * @param endDate   the end date of the report.
     */
    @FXML
    void setData(LocalDate startDate, LocalDate endDate) {

        ArrayList<Order_Details> orders;
        orders = Database_Statements.getOrderReport(startDate, endDate);

        if (orders.isEmpty())
            CommonFunctions.validationError(ValidationErrorType.NOREPORT);

        reportLabel.setText("Orders placed between '" + Main.dtf.format(startDate) + "' and '" + Main.dtf.format(endDate) + "'");

        orderNumCol.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
        customerNumCol.setCellValueFactory(new PropertyValueFactory<>("customerNumber"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        orderDateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        employeeNumCol.setCellValueFactory(new PropertyValueFactory<>("employeeNumber"));

        reportTable.getColumns().clear();
        reportTable.getColumns().addAll(orderNumCol, customerNumCol, priceCol, orderDateCol, employeeNumCol);


        int numOfOrders = 0;
        double totalPrice = 0;

        for (Order_Details order : orders) {

            reportTable.getItems().add(order);

            numOfOrders++;
            totalPrice += order.getPrice();
        }

        if (numOfOrders > 12) {
            reportTable.setPrefWidth(1015);
        }

        numOfOrdersTitle.setCellValueFactory(new PropertyValueFactory<>("numOfOrdersTitle"));
        numOfOrdersCol.setCellValueFactory(new PropertyValueFactory<>("numOfOrders"));
        unitPriceTitle.setCellValueFactory(new PropertyValueFactory<>("unitPriceTitle"));
        unitPriceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        vatPriceTitle.setCellValueFactory(new PropertyValueFactory<>("vatPriceTitle"));
        vatPriceCol.setCellValueFactory(new PropertyValueFactory<>("vatPrice"));
        grossPriceTitle.setCellValueFactory(new PropertyValueFactory<>("grossPriceTitle"));
        grossPriceCol.setCellValueFactory(new PropertyValueFactory<>("grossPrice"));


        summaryTable.getColumns().clear();
        summaryTable.getColumns().addAll(numOfOrdersTitle, numOfOrdersCol, unitPriceTitle, unitPriceCol, vatPriceTitle,
                vatPriceCol, grossPriceTitle, grossPriceCol);

        Order_Summary summary = new Order_Summary(numOfOrders, totalPrice);
        summaryTable.getItems().add(summary);

    }

    /**
     * Prints the order report
     */
    @FXML
    private void handlePrintButton() {
        //todo
    }

    /**
     * Returns the user to the screen where they select the date range for the report.
     */
    @FXML
    private void handleBackButton() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/GenerateReportsScreen2.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            CommonFunctions.uiError(e);
        }

        Stage primaryStage = (Stage) backButton.getScene().getWindow();

        GenerateReportScreen2Controller newController = loader.getController();
        newController.setData("Orders", primaryStage);


        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
    }

}
