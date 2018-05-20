package reports;

/**
 * Describes the details that are displayed in a stock report.
 * The prices in the report are not formatted so that they can be sorted from low to high as when they are formatted
 * they become a String
 *
 * @author Ryan Junor
 */
public class Stock_Details {

    private int itemNum;
    private String itemName;
    private String itemStock;
    private double itemPrice;
    private double sumPrice;

    /**
     * Creates an instance of Stock_Details with an item number, item name, stock level and price. Then calculates the total
     * cost of each item in stock
     *
     * @param itemNumber the item's number
     * @param itemName   the items name
     * @param itemStock  the items stock level
     * @param price      the items price
     */
    public Stock_Details(int itemNumber, String itemName, String itemStock, double price) {
        this.itemNum = itemNumber;
        this.itemName = itemName;
        this.itemStock = itemStock;
        this.itemPrice = price;
        this.sumPrice = Math.round((Double.parseDouble(itemStock) * itemPrice) * 100);
        this.sumPrice = this.sumPrice / 100;

    }

    public int getItemNum() {
        return itemNum;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemStock() {
        return itemStock;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public double getSumPrice() {
        return sumPrice;
    }
}
