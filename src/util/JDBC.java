package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Responsible for opening, getting, and closing the connection to the database.
 */
public abstract class JDBC {

    /**
     * Protocol part of string jdbcUrl
     */
    private static final String protocol = "jdbc";
    /**
     * Vendor part of string jdbcUrl
     */
    private static final String vendor = ":mysql:";
    /**
     * Location part of string jdbcUrl
     */
    private static final String location = "//localhost/";
    /**
     * Database name part of jdbcUrl
     */
    private static final String databaseName = "client_schedule";
    /**
     * jdbcUrl used in the connection interface
     */
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    /**
     * used to locate driver
     */
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    /**
     * username used to login
     */
    private static final String userName = "sqlUser"; // Username
    /**
     * password credential used to login
     */
    private static String password = "Passw0rd!"; // Password
    /**
     * Connection object to get the connection
     */
    public static Connection connection;  // Connection Interface

    /**
     * Makes the connection to the database when called.
     * @return the Connection connection
     */
    public static Connection openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(SQLException | ClassNotFoundException e)
        {
            //System.out.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Gets the connection to the database without having to open the connection again.
     * @return the Connection connection
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Terminates the connection to the database.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            //toss away
        }
    }
}
