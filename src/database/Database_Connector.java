package database;

import logic.CommonFunctions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A class for creating a connection to the database.
 *
 * @author Ryan Junor
 */
public class Database_Connector {

    private static Connection connect = null;

    /**
     * Creates a connection to the database.
     *
     * @return the connection if it manages to connect, if not, it throws a SQLException.
     */
    public static Connection connect() {

        try {
            connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost/graded_unit?autoReconnect=true&useSSL=false&" + "user=root&password=ryan");
        } catch (SQLException e) {
            CommonFunctions.sqlError();
        }
        return connect;
    }

    /**
     * Closes the connection to the database.
     */
    public static void disconnect() {

        try {
            connect.close();
        } catch (SQLException e) {
            CommonFunctions.sqlError();

        }
    }
}

