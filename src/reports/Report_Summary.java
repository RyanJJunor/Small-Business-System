package reports;

import logic.Main;

/**
 * Describes the common fields of all of the summary reports
 *
 * @author Ryan Junor
 */
class Report_Summary {

    private String unitPrice;
    private String vatPrice;
    private String grossPrice;

    /**
     * Creates an instance of Report_Summary and calculates the reports total unit price and total vat price.
     *
     * @param totalPrice the total price of the report
     */
    Report_Summary(double totalPrice) {

        double VAT = 1.2;
        double uPrice = (Math.round((totalPrice / VAT) * 100));
        double vPrice = (Math.round(totalPrice * 100 - uPrice));

        this.unitPrice = Main.dcf.format(uPrice / 100);
        this.vatPrice = Main.dcf.format(vPrice / 100);
        this.grossPrice = Main.dcf.format(totalPrice);
    }

    public String getUnitPriceTitle() {
        return "Total Unit Price: ";
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public String getVatPriceTitle() {
        return "Total Vat Price: ";
    }

    public String getVatPrice() {
        return vatPrice;
    }

    public String getGrossPriceTitle() {
        return "Total Gross Price: ";
    }

    public String getGrossPrice() {
        return grossPrice;
    }


}
