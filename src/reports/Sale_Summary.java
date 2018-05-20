package reports;

/**
 * Describes the details that are displayed in a sale reports summary.
 *
 * @author Ryan Junor
 */
public class Sale_Summary extends Report_Summary {

    private int unitsSold;

    /**
     * Creates an instance of Sale_Summary with the total number of items sold, the gross price of items in
     * the report and creates an instance of the super class using the gross price
     *
     * @param unitsSold  the total amount of items sold in the report
     * @param grossPrice the total price of all of the items sold in the report
     */
    public Sale_Summary(int unitsSold, double grossPrice) {
        super(grossPrice);
        this.unitsSold = unitsSold;
    }

    public String getUnitsSoldTitle() {
        return "Total Units Sold: ";
    }

    public int getUnitsSold() {
        return unitsSold;
    }
}
