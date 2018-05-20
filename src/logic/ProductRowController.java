package logic;

import database.Database_Statements;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;


/**
 * The controller for the ProductRow FXML file
 * I created this to act as a product row that can easily be added and removed, proud of this.
 *
 * @author Ryan Junor
 */
public class ProductRowController extends HBox {

    private static int number = 1;
    @FXML
    private Label numberField;
    @FXML
    private ComboBox<String> nameCombo;
    @FXML
    private TextField quantityField;
    @FXML
    private Label priceField;
    @FXML
    private ImageView image;
    private int rowNum;


    /**
     * Constructs an instance of ProductRow and assigns it a number
     */
    ProductRowController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/ui/fxml/ProductRow.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();

        } catch (Exception e) {
            CommonFunctions.uiError(e);
        }

        rowNum = number;

        numberField.setText(String.valueOf(number));

        number++;
    }

    public static int getNumber() {
        return number;
    }

    public static void setNumber(int newNumber) {
        number = newNumber;
    }

    /**
     * Gets the price of the item that the customer has selected
     *
     * @return The price of the item in the combo box
     */
    @FXML
    private double getProductPrice() {

        String item;
        String itemNum = null;

        double price;
        item = nameCombo.getSelectionModel().getSelectedItem();

        try {
            itemNum = item.substring(0, item.indexOf(" "));
        } catch (NullPointerException e) {
            //this happens when the price tries to update but there is no item in the row
        }
        price = Database_Statements.getPrice(itemNum);

        return price;
    }

    /**
     * Updates the price label with the price of one item multiplied by the entered quantity
     */
    public void handleQuantity() {
        double quantity;

        try {
            if (quantityField.getText().equals("") || Integer.parseInt(quantityField.getText()) < 0) {
                priceField.setText("");

                GridPane grid = (GridPane) priceField.getParent().getParent().getParent();
                SaleMethods.updateTotal(grid);
            } else {
                quantity = Double.parseDouble(quantityField.getText());

                priceField.setText(String.valueOf(Main.dcf.format((quantity * getProductPrice()))));

                GridPane grid = (GridPane) priceField.getParent().getParent().getParent();
                SaleMethods.updateTotal(grid);
            }
        } catch (NumberFormatException e) {
            // does not affect application
        }
    }

    /**
     * Removes the row and orders the remaining rows in the grid pane ensuring that here are no gaps and resets the static
     * variable for counting the rows to what it should be.
     */
    public void removeItem() {
        //Really proud of this code
        GridPane grid = (GridPane) numberField.getParent().getParent();

        grid.getChildren().remove(rowNum - 1);

        int newNum = 1;

        ProductRowController row;

        for (int i = 0; i < grid.getChildren().size(); i++) {
            row = (ProductRowController) grid.getChildren().get(i);

            GridPane.setRowIndex(row, i);
            row.setNumberField(i + 1);
            row.setRowNum(i + 1);
            newNum++;
        }

        number = newNum;

        SaleMethods.updateTotal(grid);

    }


    void setRemoveVisible(boolean visible) {
        image.setVisible(visible);
    }

    public int getRowNum() {
        return rowNum;
    }

    private void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    private void setNumberField(int number) {
        numberField.setText(String.valueOf(number));
    }

    /**
     * Sets the remove button on the first row to not visible and fills the combo box with all of the products in
     * the database and sets the quantity field to only accept values with less than 3 digits
     */
    @FXML
    private void initialize() {

        /*if (number == 1) {
            image.setVisible(false);
        }*/

        ArrayList<String> products;

        products = Database_Statements.getProducts();

        nameCombo.getItems().addAll(products);

        int maxQuantityLength = 2;

        quantityField.textProperty().addListener(((observable, oldValue, newValue) -> {

            if(newValue.length() > maxQuantityLength)
                quantityField.setText(oldValue);


        }));


    }
}


