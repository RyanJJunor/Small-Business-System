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
import reports.Sale_Details;
import reports.Sale_Summary;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The controller of the SalesReportScreen FXML file
 *
 * @author Ryan Junor
 */
public class SalesReportScreenController {

    @FXML
    private Button backButton;
    @FXML
    private Label reportLabel;

    @FXML
    private TableView<Sale_Details> reportTable;

    @FXML
    private TableColumn<Sale_Details, Integer> itemNumCol;
    @FXML
    private TableColumn<Sale_Details, String> itemNameCol;
    @FXML
    private TableColumn<Sale_Details, Double> itemPriceCol;
    @FXML
    private TableColumn<Sale_Details, Integer> itemQuantityCol;
    @FXML
    private TableColumn<Sale_Details, Double> sumPriceCol;


    @FXML
    private TableView<Sale_Summary> summaryTable;

    @FXML
    private TableColumn<Sale_Summary, Integer> unitsSoldCol;
    @FXML
    private TableColumn<Sale_Summary, String> unitsSoldTitle;
    @FXML
    private TableColumn<Sale_Summary, String> unitPriceTitle;
    @FXML
    private TableColumn<Sale_Summary, String> unitPriceCol;
    @FXML
    private TableColumn<Sale_Summary, String> vatPriceTitle;
    @FXML
    private TableColumn<Sale_Summary, String> vatPriceCol;
    @FXML
    private TableColumn<Sale_Summary, String> grossPriceTitle;
    @FXML
    private TableColumn<Sale_Summary, String> grossPriceCol;


    /**
     * Initialises the report table's columns and populates them with data from Sale_Details objects, initialises the
     * summary table's columns and populates them with data from Sale_Summary objects and sets the text for the
     * report label to the time frame represented in the report.
     *
     * @param startDate the start date of the report.
     * @param endDate   the end date of the report.
     */
    @FXML
    void setData(LocalDate startDate, LocalDate endDate) {

        ArrayList<Sale_Details> sales;
        sales = Database_Statements.getSaleReport(startDate, endDate);

        if (sales.isEmpty()) {
            CommonFunctions.validationError(ValidationErrorType.NOREPORT);
        } else {

            reportLabel.setText("Items sold between '" + Main.dtf.format(startDate) + "' and '" + Main.dtf.format(endDate) + "'");

            itemNumCol.setCellValueFactory(new PropertyValueFactory<>("itemNum"));
            itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
            itemPriceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
            itemQuantityCol.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
            sumPriceCol.setCellValueFactory(new PropertyValueFactory<>("sumPrice"));

            reportTable.getColumns().clear();
            reportTable.getColumns().addAll(itemNumCol, itemNameCol, itemPriceCol, itemQuantityCol, sumPriceCol);

            int unitsSold = 0;
            double totalPrice = 0;
            int rows = 0;

            for (Sale_Details sale : sales) {

                reportTable.getItems().add(sale);

                unitsSold += Integer.parseInt(sale.getItemQuantity());
                totalPrice += (sale.getItemPrice() * Double.parseDouble(sale.getItemQuantity()));

                rows++;

            }

            if (rows > 12) {
                reportTable.setPrefWidth(1015);
            }

            unitsSoldCol.setCellValueFactory(new PropertyValueFactory<>("unitsSold"));
            unitsSoldTitle.setCellValueFactory(new PropertyValueFactory<>("unitsSoldTitle"));
            unitPriceTitle.setCellValueFactory(new PropertyValueFactory<>("unitPriceTitle"));
            unitPriceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            vatPriceTitle.setCellValueFactory(new PropertyValueFactory<>("vatPriceTitle"));
            vatPriceCol.setCellValueFactory(new PropertyValueFactory<>("vatPrice"));
            grossPriceTitle.setCellValueFactory(new PropertyValueFactory<>("grossPriceTitle"));
            grossPriceCol.setCellValueFactory(new PropertyValueFactory<>("grossPrice"));


            summaryTable.getColumns().clear();
            summaryTable.getColumns().addAll(unitsSoldTitle, unitsSoldCol, unitPriceTitle, unitPriceCol, vatPriceTitle,
                    vatPriceCol, grossPriceTitle, grossPriceCol);

            Sale_Summary summary = new Sale_Summary(unitsSold, totalPrice);
            summaryTable.getItems().add(summary);
        }
    }

    /**
     * Prints the sale report.
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
        newController.setData("Sales", primaryStage);


        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
    }

}
