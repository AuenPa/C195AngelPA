package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DBCustomers;

/**
 * Represents a customer.
 */
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

    /**
     *
     * @return the customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     *
     * @param customerId the customer ID to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     *
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     *
     * @param customerName the customer name to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     *
     * @return the customer address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address the customer address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return the customer postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     *
     * @param postalCode the customer postal code to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     *
     * @return the customer phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @param phoneNumber the customer phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * @return the customer division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     *
     * @param divisionId the customer division ID to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     *
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     *
     * @param division the division to set
     */
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
