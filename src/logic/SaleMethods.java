package logic;

import database.Database_Statements;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import logic.enums.ValidationErrorType;

import java.util.ArrayList;


/**
 * A class containing methods that are shared between the two processing sales controllers.
 *
 * @author Ryan Junor
 */
class SaleMethods {

    private static ArrayList<Integer> itemsArray = new ArrayList<>();
    private static ArrayList<Integer> quantitiesArray = new ArrayList<>();
    static double totalPrice = 0;


    /**
     * This method performs validation on the order details supplied by the user, if everything is valid then the method returns true;
     *
     * @param grid the GridPane that contains the products to be ordered's details.
     * @return a boolean that will be true if the order is valid and false if not.
     */
    static boolean makeSale(GridPane grid) {

        totalPrice = 0;

        itemsArray.clear();
        quantitiesArray.clear();

        boolean validSale = false;

        int orderNum = 0;

        ProductRowController currentRow;

        ComboBox<String> combo;

        int itemNum = 0;

        String quantity;
        TextField quantityField;
        boolean validQuantity = true;

        String item;
        boolean duplicate = false;
        int currentItem = 0;

        boolean enoughStock = true;
        int currentItemStock = 0;

        try {
            for (int count = 0; count < grid.getChildren().size(); count++) {

                currentRow = (ProductRowController) grid.getChildren().get(count);

                combo = (ComboBox<String>) currentRow.getChildren().get(1);

                item = combo.getSelectionModel().getSelectedItem();
                try {
                    itemNum = Integer.parseInt(item.substring(0, item.indexOf(" ")));
                } catch (NullPointerException e) {
                    CommonFunctions.validationError(ValidationErrorType.EMPTY);
                    itemNum=-1;
                    break;

                }

                quantityField = (TextField) currentRow.getChildren().get(2);
                quantity = quantityField.getText();

                if (Integer.parseInt(quantity) <= 0) {
                    validQuantity = false;
                    CommonFunctions.validationError(ValidationErrorType.QUANTITY);
                    break;
                } else if (itemsArray.contains(itemNum)) {
                    duplicate = true;
                    break;
                } else {
                    currentItemStock = Database_Statements.getStockLevel(itemNum);
                    currentItem = itemNum;
                }
                if ((Integer.parseInt(quantity)) > currentItemStock) {
                    enoughStock = false;
                    break;
                } else {
                    itemsArray.add(itemNum);
                    quantitiesArray.add(Integer.parseInt(quantity));

                    totalPrice += (Database_Statements.getPrice(Integer.toString(itemNum)) * Double.parseDouble(quantity));
                }
            }

            if (!duplicate && enoughStock && validQuantity && (grid.getChildren().size() != 0) && itemNum!=-1) {

                for (int count = 0; count < itemsArray.size(); count++) {
                    Database_Statements.updateStock((Database_Statements.getStockLevel(itemsArray.get(count)) - quantitiesArray.get(count)), itemsArray.get(count));
                }
                validSale = true;
            } else if (duplicate)
                CommonFunctions.validationError(ValidationErrorType.DUPLICATE);
            else if (grid.getChildren().size() == 0)
                CommonFunctions.validationError(ValidationErrorType.EMPTY);
            else if (!enoughStock) {
                Alert info = new Alert(Alert.AlertType.ERROR, "Stock level of item " + currentItem + " is only " + currentItemStock, ButtonType.CLOSE);
                info.setHeaderText("Not Enough Stock");
                info.setTitle("Error");
                info.showAndWait();
            }

        } catch (NumberFormatException nfe) {
            CommonFunctions.validationError(ValidationErrorType.QUANTITY);
        } catch (NullPointerException npe) {
            CommonFunctions.validationError(ValidationErrorType.EMPTY);
        }

        return validSale;
    }

    /**
     * Processes the sale
     *
     * @param custNumber a string containing the customers customer number
     * @param totalPrice a double containing the total price of the sale
     */
    static void sale(String custNumber, double totalPrice) {
        int orderNum;

        orderNum = Database_Statements.createOrder(Integer.parseInt(custNumber), Main.activeUser.getEmployeeNumber(), totalPrice);
        Database_Statements.createOrderedItems(orderNum, itemsArray, quantitiesArray);
        if (orderNum != 0) {
            Alert info = new Alert(Alert.AlertType.INFORMATION, "Order " + orderNum + " processed", ButtonType.OK);
            info.setHeaderText("Order Successfully Processed");
            info.setTitle("Order Processed");
            info.showAndWait();
        } else {
            CommonFunctions.validationError(ValidationErrorType.NOITEMS);
        }
    }


    /**
     * A method that updates the price total when any changes to the products in the order are made.
     *
     * @param grid the GridPane that contains the products to be ordered's details.
     */
    static void updateTotal(GridPane grid) {

        totalPrice = 0;

        GridPane bigG;

        bigG = (GridPane) grid.getParent().getParent().getParent().getParent();

        HBox hb;
        hb = (HBox) bigG.getChildren().get(6);

        Label label;
        label = (Label) hb.getChildren().get(0);

        HBox rowBox;
        HBox priceBox;

        Label prices;

        for (int count = 0; count < grid.getChildren().size(); count++) {

            rowBox = (HBox) grid.getChildren().get(count);

            priceBox = (HBox) rowBox.getChildren().get(3);

            prices = (Label) priceBox.getChildren().get(0);

            try {
                totalPrice += Double.parseDouble(prices.getText().substring(1, prices.getText().length()));
            } catch (StringIndexOutOfBoundsException e) {
                //does not effect application
            }
        }

        label.setText("Total: " + Main.dcf.format(totalPrice));
    }

    /**
     * This method creates a new row to add another product and places it in the GridPane below the previous item.
     *
     * @param grid the GridPane that contains the products to be ordered's details.
     */
    static void addItem(GridPane grid) {

        ProductRowController productRowController;

        productRowController = new ProductRowController();

        grid.add(productRowController, 0, ProductRowController.getNumber() - 2, 4, 1);


    }

}

