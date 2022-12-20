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
            String sql = "SELECT * from customers";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                Customer c = new Customer(customerId, customerName, address, postalCode, phoneNumber, divisionId);
                clist.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;

    }

    public static void deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE from customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerId);
        ps.executeUpdate();
    }

}
