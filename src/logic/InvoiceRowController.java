package logic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


/**
 * The controller for the InvoiceRow FXML file
 *
 * @author Ryan Junor
 */
class InvoiceRowController extends HBox {

    private static int number = 1;
    @FXML
    private Label numberLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Label noVatLabel;
    @FXML
    private Label vatLabel;
    @FXML
    private Label totalLabel;

    InvoiceRowController(String itemName, String quantity, double price) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/ui/fxml/InvoiceRow.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();

        } catch (Exception e) {
            CommonFunctions.uiError(e);
        }

        double grossPrice = price;
        double VAT = 1.2;
        double unitPrice = (Math.round((price / VAT) * 100));
        double vatPrice = (Math.round(price * 100 - unitPrice));

        //formatting since Math.round returns a long.
        unitPrice = unitPrice / 100;
        vatPrice = vatPrice / 100;

        numberLabel.setText(String.valueOf(number));
        nameLabel.setText(itemName);
        quantityLabel.setText(quantity);
        noVatLabel.setText(Main.dcf.format(unitPrice));
        vatLabel.setText(Main.dcf.format(vatPrice));
        totalLabel.setText(Main.dcf.format(grossPrice));

        number++;
    }


}