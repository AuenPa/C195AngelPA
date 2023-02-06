package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Responsible for adding, updating, deleting, and retrieving all of the customers from the database.
 */
public class DBCustomers {

    /**
     * Gets all of the customers from the database.
     * @return the ObservableList of all customers
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> clist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Division_ID, Division FROM customers, first_level_divisions WHERE customers.Division_ID = first_level_divisions.Division_ID";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                Customer c = new Customer(customerId, customerName, address, postalCode, phoneNumber, divisionId, division);
                clist.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;

    }

    /**
     * Deletes the selected customer from the database.
     * @param customerId the customer ID to set
     * @throws SQLException
     */
    public static void deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE from customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerId);
        ps.executeUpdate();
    }

    /**
     * Adds the customer to the database from the newly created instance of customer.
     * @param customerName the customer name to set
     * @param address the customer address to set
     * @param postalCode the customer postal code to set
     * @param phoneNum the customer phone number to set
     * @param divisionId the customer division ID to set
     */
    public static void addCustomer(String customerName, String address, String postalCode, String phoneNum, int divisionId) {
        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNum);
            ps.setInt(5, divisionId);
            ps.execute();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the selected customer in the database.
     * @param customerId the customer ID to set
     * @param customerName the customer name to set
     * @param address the customer address to set
     * @param postalCode the customer postal code to set
     * @param phoneNum the customer phone number to set
     * @param divisionId the customer division ID to set
     */
    public static void updateCustomer(int customerId, String customerName, String address, String postalCode, String phoneNum, int divisionId) {

        try{
            String sqlc = "UPDATE customers set Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";

            PreparedStatement pst = JDBC.getConnection().prepareStatement(sqlc);

            pst.setString(1, customerName);
            pst.setString(2, address);
            pst.setString(3, postalCode);
            pst.setString(4, phoneNum);
            pst.setInt(5, divisionId);
            pst.setInt(6, customerId);
            pst.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

}
