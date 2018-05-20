package reports;

/**
 * Describes the details that are displayed in a sale report.
 * The prices in the report are not formatted so that they can be sorted from low to high as when they are formatted
 * they become a String
 *
 * @author Ryan Junor
 */
public class Sale_Details {

    private int itemNum;
    private String itemName;
    private String itemQuantity;
    private double itemPrice;
    private double sumPrice;

    /**
     * Creates an instance of Sale_Details with an item number, item name, quantity and price. Then calculates the total
     * cost of the item
     *
     * @param itemNumber   the item's number
     * @param itemName     the items name
     * @param itemQuantity the items quantity
     * @param price        the items price
     */
    public Sale_Details(int itemNumber, String itemName, String itemQuantity, double price) {
        this.itemNum = itemNumber;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemPrice = price;
        this.sumPrice = Math.round((Double.parseDouble(itemQuantity) * itemPrice) * 100);
        this.sumPrice = this.sumPrice / 100;

    }

    public int getItemNum() {
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

    public double getSumPrice() {
        return sumPrice;
    }
}
