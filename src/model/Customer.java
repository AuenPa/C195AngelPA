package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DBCustomers;

public class Customer {
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionId;
    private String division;

    public Customer(int customerId, String customerName, String address, String postalCode, String phoneNumber, int divisionId, String division) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
        this.division = division;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    //12/26/22 9:13pm
    //public static ObservableList<Customer> displayCustomerList = FXCollections.observableArrayList();
/*
    public static void addCustomerCM(Customer CM) {
        displayCustomerList.add(CM);
    }
 */
/*
    public static ObservableList<Customer> getAllCustomersCM () {
        return displayCustomerList;
    }

 */
/*
    public static void deleteCustomerCM(Customer selectedCustomer) {
        for(Customer SC : getAllCustomersCM()) {
            if(SC.getCustomerId() == selectedCustomer.getCustomerId()) {
                getAllCustomersCM().remove(SC);
                break;
            }
        }
    }

 */
/*
    public static void addTestData() {
        for(Customer SC : DBCustomers.getAllCustomers()) {
            addCustomerCM(SC);
        }
    }

 */
    /*
    static {
        addTestData();
    }
     */
}
