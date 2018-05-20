package reports;

/**
 * Describes the details that are displayed in an order reports summary.
 *
 * @author Ryan Junor
 */
public class Order_Summary extends Report_Summary {

    private int numOfOrders;

    /**
     * Creates an instance of Order_Summary  with the number of orders in the report, the gross price of the orders in
     * the report and creates an instance of the super class using the gross price
     *
     * @param numOfOrders total number of orders in the report
     * @param grossPrice  the gross price of all of the orders in the report.
     */
    public Order_Summary(int numOfOrders, double grossPrice) {
        super(grossPrice);
        this.numOfOrders = numOfOrders;
    }

    public String getNumOfOrdersTitle() {
        return "Number of Orders: ";
    }

    public int getNumOfOrders() {
        return numOfOrders;
    }
}

