package database;

import entities.Active_User;
import entities.User_Details;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import logic.CommonFunctions;
import logic.Main;
import logic.enums.DatabaseMessageType;
import reports.Order_Details;
import reports.Order_Row;
import reports.Sale_Details;
import reports.Stock_Details;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A class containing methods for carrying out database statements.
 *
 * @author Ryan Junor
 */
public class Database_Statements {

    /**
     * Compares the entered log in details against the database to see if they are valid,
     * if valid the user progresses and is initialised as the active user.
     *
     * @param username        the username that the user has entered
     * @param enteredPassword the password that the user has entered
     * @return true if the user entered valid log in credentials, false if not.
     */
    public static String logIn(String username, String enteredPassword) {

        String validLogIn = "no";

        String query = "SELECT password "
                + "FROM employees "
                + "WHERE id = \"" + username + "\";";

        Connection con;

        con = Database_Connector.connect();

        try (Statement stmt = con.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {

                    if (rs.getString("password").equals(enteredPassword)) {

                        validLogIn = "yes";
                    }
                }
            }
        } catch (Exception e) {
            validLogIn = "error";
        }

        if (validLogIn.equals("yes"))
            Database_Statements.setActiveUser(username, con);

        return validLogIn;

    }

    /**
     * Initialises the active user
     *
     * @param employeeNumber the employee number of the active user
     * @param con            the connection to the database
     */
    private static void setActiveUser(String employeeNumber, Connection con) {

        String firstName;
        String lastName;
        boolean isAdmin;

        String query = "SELECT id, f_name, l_name, is_admin "
                + "FROM employees "
                + "WHERE id = \"" + employeeNumber + "\";";

        try (Statement stmt = con.createStatement()) {

            try (ResultSet rs = stmt.executeQuery(query)) {

                rs.next();

                firstName = rs.getString("f_name");
                lastName = rs.getString("l_name");
                isAdmin = rs.getBoolean("is_admin");
            }

            Main.activeUser = new Active_User(Integer.parseInt(employeeNumber), firstName, lastName, isAdmin);

            con.close();

        } catch (SQLException e) {
            // should never happen
        }

        Database_Connector.disconnect();
    }

    /**
     * Inserts a new user in to the database with the default password
     *
     * @param username  the user to add's employee number
     * @param firstName the user to add's first name
     * @param lastName  the user to add's last name
     * @param isAdmin   whether the user is an admin or not
     */
    public static void addUser(String username, String firstName, String lastName, boolean isAdmin) {

        String query = "INSERT INTO employees "
                + "VALUES('" + username + "', '" + firstName + "', '" + lastName + "', " + isAdmin + ", default);";

        try (Connection con = Database_Connector.connect()) {

            Statement stmt = con.createStatement();

            int rowsAffected = stmt.executeUpdate(query);


            if (rowsAffected != 0) {
                CommonFunctions.SuccessMessage(DatabaseMessageType.USERADDED);
            } else
                CommonFunctions.failMessage(DatabaseMessageType.USERNOTADDED);

        } catch (SQLException e) {
            String message = "User not created.\nA user with that employee number already exists.";

            Alert info = new Alert(Alert.AlertType.ERROR, message, ButtonType.CLOSE);
            info.setHeaderText("Cannot Add User");
            info.setTitle("Add User");

            info.show();
        }

        Database_Connector.disconnect();
    }


    /**
     * Removes a user from the database using their employee number
     *
     * @param username the employee number of the user to remove.
     */
    public static void removeUser(String username) {

        String query = "DELETE FROM employees "
                + "WHERE id ='" + username + "';";

        try (Connection con = Database_Connector.connect()) {

            Statement stmt = con.createStatement();

            int rowsAffected = stmt.executeUpdate(query);

            if (rowsAffected != 0) {
                CommonFunctions.SuccessMessage(DatabaseMessageType.USERREMOVED);
            } else
                CommonFunctions.failMessage(DatabaseMessageType.USERNOTREMOVED);

        } catch (SQLException e) {
            //should be covered by validation
        }

        Database_Connector.disconnect();

    }

    /**
     * Resets the password of a user to the default password
     *
     * @param username the employee number of the user who's password is to be reset
     */
    public static void resetPassword(String username) {

        String query = "UPDATE employees "
                + "SET password = default "
                + "WHERE id ='" + username + "';";

        try (Connection con = Database_Connector.connect()) {

            Statement stmt = con.createStatement();

            int rowsAffected = stmt.executeUpdate(query);

            if (rowsAffected != 0) {
                CommonFunctions.SuccessMessage(DatabaseMessageType.PASSWORDRESET);
            } else
                CommonFunctions.failMessage(DatabaseMessageType.PASSWORDNOTRESET);

        } catch (SQLException e) {
            //should be covered by validation
        }
        Database_Connector.disconnect();
    }

    /**
     * Changes the password of a user to the specified password.
     *
     * @param username the employee number of the user who's password is to be changed.
     * @param password the new password for the user.
     */
    public static void changePassword(String username, String password) {

        String query = "UPDATE employees "
                + "SET password = '" + password + "' "
                + "WHERE id ='" + username + "';";

        try (Connection con = Database_Connector.connect()) {

            Statement stmt = con.createStatement();

            int rowsAffected = stmt.executeUpdate(query);

            if (rowsAffected != 0) {
                CommonFunctions.SuccessMessage(DatabaseMessageType.PASSWORDCHANGED);
            } else
                CommonFunctions.failMessage(DatabaseMessageType.PASSWORDNOTCHANGED);

        } catch (SQLException e) {
            //should be covered by validation
        }
        Database_Connector.disconnect();
    }

    /**
     * Gets the product name and number of all of the products in the database
     *
     * @return an ArrayList of Strings containing the product number and product name of all products in the database.
     */
    public static ArrayList<String> getProducts() {

        ArrayList<String> products = new ArrayList<>();

        String query = "SELECT number, name "
                + "FROM items;";

        try {
            try (Connection con = Database_Connector.connect()) {

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    products.add(rs.getString("number") + " " + rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            //should be covered by validation
        }

        Database_Connector.disconnect();

        return products;
    }

    /**
     * Gets the stock level of the specified item.
     *
     * @param itemNum the item number of the item to get the stock level of.
     * @return The stock level of the specified item as an int
     */
    public static int getStockLevel(int itemNum) {

        int stockLevel = 0;

        String query = "SELECT quantity "
                + "FROM items "
                + "WHERE number =" + itemNum + ";";

        try {
            try (Connection con = Database_Connector.connect()) {

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    stockLevel = rs.getInt("quantity");
                }
            }
        } catch (SQLException e) {
            //should be covered by validation
        }

        Database_Connector.disconnect();

        return stockLevel;
    }

    /**
     * Updates the database with the new specified stock level for the specified item.
     *
     * @param newStockLevel the new stock level
     * @param itemNum       the item number of the item to change
     */
    public static void updateStock(int newStockLevel, int itemNum) {

        String query = "UPDATE items "
                + "SET quantity = '" + newStockLevel + "' "
                + "WHERE number ='" + itemNum + "';";

        try (Connection con = Database_Connector.connect()) {

            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);

        } catch (SQLException e) {
            //should be covered by validation
        }

        Database_Connector.disconnect();

    }

    /**
     * A method for getting an ArrayList of the customers in the database
     *
     * @return an ArrayList populated with each element populated with the account number, first name and last name concatenated together.
     */
    public static ArrayList<String> getCustomers() {

        ArrayList<String> customers = new ArrayList<>();

        String query = "SELECT number, first_name, last_name "
                + "FROM customers;";

        try (Connection con = Database_Connector.connect()) {

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                customers.add(rs.getString("number") + " " + rs.getString("first_name") + " " + rs.getString("last_name"));
            }
        } catch (SQLException e1) {
            //should be covered by validation
        }

        Database_Connector.disconnect();

        return customers;
    }

    /**
     * A method for populating the Labels on the sale screen with the user who is purchasing the sale.
     *
     * @param customerNumber the account number of the user purchasing the sale
     * @return An ArrayList containing the customer's details and their address details.
     */
    public static ArrayList<String> getCustomer(String customerNumber) {

        ArrayList<String> customerDetails = new ArrayList<>();

        String addressID;

        String query = "SELECT first_name, last_name, LOWER(email), phone_number, address "
                + "FROM customers "
                + "WHERE number = \"" + customerNumber + "\";";

        try (Connection con = Database_Connector.connect()) {
            Statement stmt = con.createStatement();

            try (ResultSet rs = stmt.executeQuery(query)) {

                rs.next();

                customerDetails.add(customerNumber);
                customerDetails.add(rs.getString("first_name"));
                customerDetails.add(rs.getString("last_name"));
                customerDetails.add(rs.getString("LOWER(email)"));
                customerDetails.add(rs.getString("phone_number"));

                addressID = rs.getString("address");

                String addressQuery = "SELECT line1, line2, city, county, postcode "
                        + "FROM address "
                        + "WHERE id = \"" + addressID + "\";";

                Statement stmt2 = con.createStatement();

                try (ResultSet rs2 = stmt2.executeQuery(addressQuery)) {

                    rs2.next();

                    customerDetails.add(rs2.getString("line1"));
                    customerDetails.add(rs2.getString("line2"));
                    customerDetails.add(rs2.getString("city"));
                    customerDetails.add(rs2.getString("county"));
                    customerDetails.add(rs2.getString("postcode"));

                } catch (SQLException e) {
                    //happens when user doesn't have an address
                }


            } catch (SQLException e) {
                //should be covered by validation
            }

        } catch (SQLException e) {
            //should be covered by validation
        }

        Database_Connector.disconnect();

        return customerDetails;
    }

    /**
     * Gets the price of a specific item
     *
     * @param itemNum the item number of the item to find the price of
     * @return A double containing the price of the specified item
     */
    public static double getPrice(String itemNum) {

        double price = 0;

        String query = "SELECT price "
                + "FROM items "
                + "WHERE number =" + itemNum + ";";

        try {
            try (Connection con = Database_Connector.connect()) {

                Statement stmt = con.createStatement();

                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    price = rs.getDouble("price");
                }
            }
        } catch (SQLException e) {
            //should be covered by validation
        }

        Database_Connector.disconnect();

        return price;
    }

    /**
     * Creates an order and inserts it into the database
     *
     * @param customerNumber the customer number of the customer placing the order
     * @param employeeNumber the employee number of the employee that is processing the order
     * @param totalPrice     the total price of the order
     * @return An int containing the order number of the order just created.
     */
    public static int createOrder(int customerNumber, int employeeNumber, double totalPrice) {

        String insertQuery = "INSERT INTO orders(price, customer_number, employee_number) "
                + "VALUES(" + totalPrice + ", " + customerNumber + ", " + employeeNumber + ");";

        int orderNum = 0;

        try (Connection con = Database_Connector.connect()) {

            Statement insertStmt = null;
            try {
                insertStmt = con.createStatement();
            } catch (SQLException e) {
                //should be covered by validation
            }

            try {
                insertStmt.executeUpdate(insertQuery);
            } catch (SQLException e) {
                //should be covered by validation
            }

            String selectQuery = "SELECT MAX(order_number) "
                    + "FROM orders;";

            Statement selectStmt = null;
            try {
                selectStmt = con.createStatement();
            } catch (SQLException e) {
                //should be covered by validation
            }

            ResultSet rs = null;
            try {
                rs = selectStmt.executeQuery(selectQuery);
            } catch (SQLException e) {
                //should be covered by validation
            }

            while (rs.next()) {
                try {
                    orderNum = rs.getInt("MAX(order_number)");
                } catch (SQLException e) {
                    //should be covered by validation
                }
            }

        } catch (SQLException e) {
            //should be covered by validation
        }
        Database_Connector.disconnect();
        return orderNum;
    }

    /**
     * Adds the items ordered to the ordered items table in the database
     *
     * @param orderNumber the order number of the order beign processed
     * @param items       the item numbers of the items being ordered
     * @param quantities  the quantities of the items being ordered
     */
    public static void createOrderedItems(int orderNumber, ArrayList<Integer> items, ArrayList<Integer> quantities) {

        try (Connection con = Database_Connector.connect()) {

            for (int count = 0; count < items.size(); count++) {
                String insertQuery = "INSERT INTO ordered_items(order_number, item_number, quantity_ordered) "
                        + "VALUES(" + orderNumber + ", " + items.get(count) + ", " + quantities.get(count) + ");";

                Statement insertStmt = con.createStatement();

                insertStmt.executeUpdate(insertQuery);

            }

        } catch (SQLException e) {
            //should be covered by validation
        }

        Database_Connector.disconnect();
    }

    /**
     * Inserts a new address in to the database
     *
     * @param line1    the first line of the address
     * @param line2    the second line of the address
     * @param city     the city of the address
     * @param county   the first line of the address
     * @param postcode the postcode of the address
     */
    public static void createAddress(String line1, String line2, String city, String county, String postcode) {

        if(!(line2 == null))
            line2 = "'"+line2+"'";

        if(!(county == null))
            county = "'"+county+"'";


        try (Connection con = Database_Connector.connect()) {

            String insertQuery = "INSERT INTO address(line1, line2, city, county, postcode) "
                    + "VALUES('" + line1 + "', " + line2 + ", '" + city + "', " + county + ",'" + postcode + "');";

            Statement insertStmt = con.createStatement();

            insertStmt.executeUpdate(insertQuery);

        } catch (SQLException e) {
            //should be covered by validation
        }
        Database_Connector.disconnect();
    }

    /**
     * Inserts a new user to the database and assigns their address
     *
     * @param firstName the first name of the customer being created
     * @param lastName  the last name of the customer being created
     * @param email     the email address of the customer being created
     * @param phone     the phone number of the customer being created
     * @param address   the integer that links to the address of the customer being created
     */
    public static void createCustomer(String firstName, String lastName, String email, String phone, int address) {

        try (Connection con = Database_Connector.connect()) {

            String insertQuery = "INSERT INTO customers(first_name, last_name, email, phone_number, address) "
                    + "VALUES('" + firstName + "', '" + lastName + "', '" + email + "', '" + phone + "'," + address + ");";

            Statement insertStmt = con.createStatement();

            insertStmt.executeUpdate(insertQuery);

        } catch (SQLException e) {
            //should be covered by validation
        }
        Database_Connector.disconnect();
    }

    /**
     * Creates a new customer but does not assign an address.
     *
     * @param firstName The first name of the customer being created.
     * @param lastName  The last name of the customer being created
     * @param email     The email address of the customer being created
     * @param phone     The phone number of the customer being created
     */
    public static void createCustomerNoAddress(String firstName, String lastName, String email, String phone) {

        try (Connection con = Database_Connector.connect()) {

            String insertQuery = "INSERT INTO customers(first_name, last_name, email, phone_number) "
                    + "VALUES('" + firstName + "', '" + lastName + "', '" + email + "', '" + phone + "');";

            Statement insertStmt = con.createStatement();

            insertStmt.executeUpdate(insertQuery);

        } catch (SQLException e) {
            //should be covered by validation
        }

        Database_Connector.disconnect();

    }

    /**
     * Gets the address number of the address just added
     *
     * @return an int that stores the most recent address created.
     */
    public static int getNewAddressNum() {

        int addressNum = 0;

        String query = "SELECT MAX(id) "
                + "FROM address;";

        try {
            try (Connection con = Database_Connector.connect()) {

                Statement stmt = con.createStatement();

                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    addressNum = rs.getInt("MAX(id)");
                }
            }
        } catch (SQLException e) {
            //should be covered by validation
        }

        Database_Connector.disconnect();

        return addressNum;
    }

    /**
     * Gets the next account number to be assigned
     *
     * @return an int that stores the account number to be assigned
     */
    public static int getNewAccountNum() {

        int accountNum = 0;

        String query = "SELECT MAX(number) "
                + "FROM customers;";

        try {
            try (Connection con = Database_Connector.connect()) {

                Statement stmt = con.createStatement();

                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    accountNum = rs.getInt("MAX(number)");
                }
            }
        } catch (SQLException e) {
            //should be covered by validation
        }

        Database_Connector.disconnect();

        accountNum++;

        return accountNum;
    }

    /**
     * Gets details of all of the orders the specified customer has placed
     *
     * @param customerNumber the customer number of the customer who orders are being gotten.
     * @return an ArrayList of strings containing the order number, order date and total price of the order
     */
    public static ArrayList<String> getCustomerOrders(int customerNumber) {

        ArrayList<String> orders = new ArrayList<>();

        String query = "SELECT order_number, order_date, price "
                + "FROM orders "
                + "WHERE customer_number = " + customerNumber
                + " ORDER BY order_date DESC;";

        try (Connection con = Database_Connector.connect()) {

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                orders.add("#" + rs.getInt("order_number") + " Date: " +
                        Main.sdt.format(rs.getDate("order_date")) + " Price: " + Main.dcf.format(rs.getDouble("price")));
            }
        } catch (SQLException e) {
            //should be covered by validation
        }
        Database_Connector.disconnect();

        return orders;
    }

    /**
     * Gets the item number, item name, quantity, price and employee number of an order.
     *
     * @param orderNumber The order number of the order to get the details about/
     * @return An ArrayList containing an object describing the order
     */
    public static ArrayList<Order_Row> getCustomerOrder(int orderNumber) {

        ArrayList<Order_Row> order = new ArrayList<>();


        String query = "SELECT items.number, items.name, ordered_items.quantity_ordered, items.price, orders.employee_number "
                + "FROM ordered_items "
                + "INNER JOIN items  ON items.number = ordered_items.item_number AND ordered_items.order_number = " + orderNumber
                + " INNER JOIN orders ON orders.order_number = ordered_items.order_number;";


        try (Connection con = Database_Connector.connect()) {

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                Order_Row row = new Order_Row(String.valueOf(rs.getInt("items.number")),
                        rs.getString("items.name"),
                        String.valueOf(rs.getInt("ordered_items.quantity_ordered")),
                        rs.getDouble("items.price"), String.valueOf(rs.getInt("orders.employee_number")));

                order.add(row);
            }
        } catch (SQLException e) {
            //should be covered by validation
        }

        Database_Connector.disconnect();

        return order;
    }

    /**
     * Gets details about all orders between two dates
     *
     * @param startDate the start date of the report
     * @param endDate   the end date of the report
     * @return An ArrayList containing objects that describe each orders details
     */
    public static ArrayList<Order_Details> getOrderReport(LocalDate startDate, LocalDate endDate) {

        ArrayList<Order_Details> orders = new ArrayList<>();

        String query = "SELECT order_number, customer_number, price, order_date, employee_number "
                + "FROM orders "
                + "WHERE order_date BETWEEN '" + startDate + "' AND '" + endDate.plusDays(1) + "'"
                + "ORDER BY order_date;";

        try (Connection con = Database_Connector.connect()) {

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                Order_Details order = new Order_Details(rs.getInt("order_number"),
                        rs.getInt("customer_number"),
                        rs.getDouble("price"),
                        Main.sdt.format(rs.getDate("order_date")),
                        rs.getInt("employee_number"));

                orders.add(order);
            }

        } catch (SQLException e) {
            //should be covered by validation
        }

        Database_Connector.disconnect();

        return orders;

    }

    /**
     * Gets details about all sales between two dates
     *
     * @param startDate the start date of the report
     * @param endDate   the end date of the report
     * @return An ArrayList containing objects that describe each sales details
     */
    public static ArrayList<Sale_Details> getSaleReport(LocalDate startDate, LocalDate endDate) {

        ArrayList<Sale_Details> sales = new ArrayList<>();

        String query = "SELECT items.number, items.name, SUM(ordered_items.quantity_ordered), items.price "
                + "FROM ordered_items "
                + "INNER JOIN items ON items.number = ordered_items.item_number "
                + "INNER JOIN orders ON orders.order_number = ordered_items.order_number "
                + "WHERE order_date BETWEEN '" + startDate + "' AND '" + endDate.plusDays(1) + "' "
                + "GROUP BY items.number, items.name, items.price;";

        try (Connection con = Database_Connector.connect()) {

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                Sale_Details sale = new Sale_Details(rs.getInt("items.number"),
                        rs.getString("items.name"),
                        String.valueOf(rs.getInt("SUM(ordered_items.quantity_ordered)")),
                        rs.getDouble("items.price"));
                sales.add(sale);
            }
        } catch (SQLException e) {
            //should be covered by validation
        }
        Database_Connector.disconnect();

        return sales;
    }


    /**
     * Gets details about all orders between two dates
     *
     * @return An ArrayList containing objects that hold details about each item.
     */
    public static ArrayList<Stock_Details> getStockReport() {

        ArrayList<Stock_Details> stock = new ArrayList<>();

        String query = "SELECT number, name, quantity, price "
                + "FROM items ;";

        try (Connection con = Database_Connector.connect()) {

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                Stock_Details item = new Stock_Details(rs.getInt("number"),
                        (rs.getString("name")), rs.getString("quantity"),
                        rs.getDouble("price"));

                stock.add(item);
            }

        } catch (SQLException e) {
            //should be covered by validation
        }

        Database_Connector.disconnect();

        return stock;

    }

    public static ArrayList<User_Details> getUsers() {

        ArrayList<User_Details> users = new ArrayList<>();

        String query = "SELECT id, f_name, l_name, is_admin "
                + "FROM employees ;";

        try (Connection con = Database_Connector.connect()) {

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                User_Details user = new User_Details(rs.getInt("id"), rs.getString("f_name"),
                        rs.getString("l_name"), rs.getBoolean("is_admin"));

                users.add(user);
            }

        } catch (SQLException e) {
            //should be covered by validation
        }

        Database_Connector.disconnect();

        return users;


    }
}
