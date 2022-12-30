package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCustomers {

    //private static ObservableList<Customer> clist = FXCollections.observableArrayList();


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

    public static void deleteCustomer(int customerId) throws SQLException {
        /*
        for(Customer SC : getAllCustomers()) {
            if(SC.getCustomerId() == customerId) {
                getAllCustomers().remove(SC);
            }
        }
         */
        //going to have to make a public static *final* observable array list to hold what is retrieved from the database using the
        //DBCustomers.getAllcustomers() method. use this list to populate the table
        String sql = "DELETE from customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerId);
        ps.executeUpdate();
    }

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

}
