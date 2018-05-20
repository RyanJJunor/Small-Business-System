package reports;

/**
 * Describes row of an order that is stored in the database
 *
 * @author Ryan Junor
 */
public class Order_Row {

    private String itemNum;
    private String itemName;
    private String itemQuantity;
    private double itemPrice;
    private String sumPrice;
    private String employeeNum;

    /**
     * Creates an instance of an Order_Row with an item number, item name, quantity, price and the employee number of
     * the employee who processed the order.
     *
     * @param itemNum      the item number of the item in the row
     * @param itemName     the name of the item in the row
     * @param itemQuantity the quantity of the item in the row
     * @param itemPrice    the price of the item in the row
     * @param employeeNum  the employee number of the employee who processed the order
     */
    public Order_Row(String itemNum, String itemName, String itemQuantity, double itemPrice, String employeeNum) {
        this.itemNum = String.valueOf(itemNum);
        this.itemName = itemName;
        this.itemQuantity = String.valueOf(itemQuantity);
        this.itemPrice = itemPrice;
        this.sumPrice = String.valueOf(Double.parseDouble(itemQuantity) * itemPrice);
        this.employeeNum = String.valueOf(employeeNum);
    }

    public String getItemNum() {
        return itemNum;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public String getSumPrice() {
        return sumPrice;
    }

    public String getEmployeeNum() {
        return employeeNum;
    }
}
