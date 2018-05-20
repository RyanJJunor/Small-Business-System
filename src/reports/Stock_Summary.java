package reports;

/**
 * Describes the details that are displayed in a sale reports summary.
 *
 * @author Ryan Junor
 */
public class Stock_Summary extends Report_Summary {

    private int totalUnits;

    /**
     * Creates an instance of Stock_Summary with the total number of items in stock, the gross price of all of the items in
     * the report and creates an instance of the super class using the gross price
     *
     * @param unitsInStock the total amount of all the items in the report
     * @param grossPrice   the total price of all of the items in the report
     */
    public Stock_Summary(int unitsInStock, double grossPrice) {
        super(grossPrice);
        this.totalUnits = unitsInStock;
    }

    public String getTotalUnitsTitle() {
        return "Total Stock Units : ";
    }

    public int getTotalUnits() {

        return totalUnits;
    }

}

