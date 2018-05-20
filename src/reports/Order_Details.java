package reports;

/**
 * Describes the details that are displayed in an order report.
 * The prices in the report are not formatted so that they can be sorted from low to high as when they are formatted
 * they become a String
 *
 * @author Ryan Junor
 */
public class Order_Details {

    private int orderNumber;
    private int customerNumber;
    private double price;
    private String orderDate;
    private int employeeNumber;

    /**
     * Creates an instance of Order_Details with an order number, customer number, price, order date and the employee number of
     * the employee who processed the order.
     *
     * @param orderNumber    the order's order number.
     * @param customerNumber the customer number of the customer who placed the order.
     * @param price          the total price of the order.
     * @param orderDate      the date that the order was placed on.
     * @param employeeNumber the employee number of the employee who processed the order
     */
    public Order_Details(int orderNumber, int customerNumber, double price, String orderDate, int employeeNumber) {
        this.orderNumber = orderNumber;
        this.customerNumber = customerNumber;
        this.price = price;
        this.orderDate = orderDate;
        this.employeeNumber = employeeNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public double getPrice() {
        return price;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

}
