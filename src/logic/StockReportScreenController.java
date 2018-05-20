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
import reports.Stock_Details;
import reports.Stock_Summary;

import java.util.ArrayList;

/**
 * The controller of the StockReportScreen FXML file
 *
 * @author Ryan Junor
 */
public class StockReportScreenController {

    @FXML
    private Button backButton;
    @FXML
    private Label reportLabel;

    @FXML
    private TableView<Stock_Details> reportTable;

    @FXML
    private TableColumn<Stock_Details, Integer> itemNumCol;
    @FXML
    private TableColumn<Stock_Details, String> itemNameCol;
    @FXML
    private TableColumn<Stock_Details, Integer> itemStockCol;
    @FXML
    private TableColumn<Stock_Details, Double> itemPriceCol;
    @FXML
    private TableColumn<Stock_Details, Double> sumPriceCol;


    @FXML
    private TableView<Stock_Summary> summaryTable;

    @FXML
    private TableColumn<Stock_Summary, String> totalUnitsTitle;
    @FXML
    private TableColumn<Stock_Summary, Integer> totalUnitsCol;
    @FXML
    private TableColumn<Stock_Summary, String> unitPriceTitle;
    @FXML
    private TableColumn<Stock_Summary, String> unitPriceCol;
    @FXML
    private TableColumn<Stock_Summary, String> vatPriceTitle;
    @FXML
    private TableColumn<Stock_Summary, String> vatPriceCol;
    @FXML
    private TableColumn<Stock_Summary, String> grossPriceTitle;
    @FXML
    private TableColumn<Stock_Summary, String> grossPriceCol;


    /**
     * Initialises the report table's columns and populates them with data from Sale_Details objects and initialises the
     * summary table's columns and populates them with data from Sale_Summary objects when the screen is initialised
     */
    @FXML
    private void initialize() {

        reportLabel.setText("Stock Report");

        itemNumCol.setCellValueFactory(new PropertyValueFactory<>("itemNum"));
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemPriceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        itemStockCol.setCellValueFactory(new PropertyValueFactory<>("itemStock"));
        sumPriceCol.setCellValueFactory(new PropertyValueFactory<>("sumPrice"));

        reportTable.getColumns().clear();
        reportTable.getColumns().addAll(itemNumCol, itemNameCol, itemPriceCol, itemStockCol, sumPriceCol);

        ArrayList<Stock_Details> stock;
        stock = Database_Statements.getStockReport();

        int totalUnits = 0;
        double totalPrice = 0;
        int rows = 0;

        for (Stock_Details item : stock) {

            reportTable.getItems().add(item);

            totalUnits += Integer.parseInt(item.getItemStock());
            totalPrice += (item.getItemPrice() * Double.parseDouble(item.getItemStock()));

            rows++;

        }

        if (rows > 12) {
            reportTable.setPrefWidth(1015);
        }

        totalUnitsTitle.setCellValueFactory(new PropertyValueFactory<>("totalUnitsTitle"));
        totalUnitsCol.setCellValueFactory(new PropertyValueFactory<>("totalUnits"));
        unitPriceTitle.setCellValueFactory(new PropertyValueFactory<>("unitPriceTitle"));
        unitPriceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        vatPriceTitle.setCellValueFactory(new PropertyValueFactory<>("vatPriceTitle"));
        vatPriceCol.setCellValueFactory(new PropertyValueFactory<>("vatPrice"));
        grossPriceTitle.setCellValueFactory(new PropertyValueFactory<>("grossPriceTitle"));
        grossPriceCol.setCellValueFactory(new PropertyValueFactory<>("grossPrice"));


        summaryTable.getColumns().clear();
        summaryTable.getColumns().addAll(totalUnitsTitle, totalUnitsCol, unitPriceTitle, unitPriceCol, vatPriceTitle,
                vatPriceCol, grossPriceTitle, grossPriceCol);

        Stock_Summary summary = new Stock_Summary(totalUnits, totalPrice);
        summaryTable.getItems().add(summary);

    }

    /**
     * Prints the stock order
     */
    @FXML
    private void handlePrintButton() {

//todo
    }

    /**
     * Returns the user to the screen where they select the report type.
     */
    @FXML
    private void handleBackButton() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/GenerateReportScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            CommonFunctions.uiError(e);
        }

        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        primaryStage.setTitle("Generate A Report");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
    }

}
