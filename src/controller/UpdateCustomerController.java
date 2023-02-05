package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;
import util.DBCountry;
import util.DBCustomers;
import util.DBDivision;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is responsible for updating customers. The changes are reflected in the database.
 */
public class UpdateCustomerController implements Initializable {

    /**
     * TextField for inputting the address for the customer.
     */
    @FXML
    private TextField address;

    /**
     * TextField for the customer ID. It is auto-generated in the database so it is not editable and is not accessed.
     */
    @FXML
    private TextField customerID;

    /**
     * TextField for inputting the customer name.
     */
    @FXML
    private TextField customerName;

    /**
     * TextField for inputting the customers phone number.
     */
    @FXML
    private TextField phoneNumber;

    /**
     * TextField for inputting the customers postal code.
     */
    @FXML
    private TextField postalCode;

    /**
     * ComboBox for selecting the customers division.
     */
    @FXML
    private ComboBox<Division> divisionComboBox;

    /**
     * ComboBox for selecting the customers country.
     */
    @FXML
    private ComboBox<Country> countryComboBox;

    /**
     * Used to store the selected customer that is passed to be modified.
     */
    private static Customer sentCustomer;

    /**
     * Used to be called in the customer controller to pass the selected customer to be modified.
     * @param customer the customer, selected in the customer table, to be modified
     */
    public static void passCustomer (Customer customer) {
        sentCustomer = customer;
    }

    /**
     * Used to store and set the passed-over customers division value in the combobox of divisions.
     */
    private Division sentCustomerDivision;

    /**
     * Used to store and set the passed-over customers country value in the country combobox.
     */
    private Country sentCustomerCountry;

    /**
     * Used to hold the divisions filtered by their respective country.
     */
    private ObservableList<Division> divisionFiltered = FXCollections.observableArrayList();

    /**
     * Populates the textfields and comboboxes with the appropriate data from the customer instance
     * that is passed.
     * Sets the comboboxes for the country and divisions to be selected.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //probably don't need this
        ObservableList<Division> slist = DBDivision.getAllDivisions();
        divisionComboBox.setItems(slist);

        ObservableList<Country> clist = DBCountry.getAllCountries();
        countryComboBox.setItems(clist);

        customerID.setText(String.valueOf(sentCustomer.getCustomerId()));
        customerName.setText(sentCustomer.getCustomerName());
        address.setText(sentCustomer.getAddress());
        postalCode.setText(sentCustomer.getPostalCode());
        phoneNumber.setText(sentCustomer.getPhoneNumber());
        int divisionId = sentCustomer.getDivisionId();
        for(Division S : DBDivision.getAllDivisions()) {
            if(S.getDivisionId() == divisionId) {
                sentCustomerDivision = S;
                break;
            }
        }
        divisionComboBox.setValue(sentCustomerDivision);


        for(Country C : DBCountry.getAllCountries()) {
            if(C.getCountryId() == sentCustomerDivision.getCountryId()) {
                sentCustomerCountry = C;
            }
        }
        countryComboBox.setValue(sentCustomerCountry);

        if(sentCustomerCountry.getCountryId() == 1) {
            divisionFiltered.clear();
            divisionComboBox.setItems(divisionFiltered);

            for(Division S : DBDivision.getAllDivisions()) {
                if(S.getCountryId() == 1) {
                    divisionFiltered.add(S);
                }
            }
            divisionComboBox.setItems(divisionFiltered);
        }

        else if(sentCustomerCountry.getCountryId() == 2) {
            divisionFiltered.clear();
            divisionComboBox.setItems(divisionFiltered);
            for(Division s : DBDivision.getAllDivisions()) {
                if(s.getCountryId() == 2) {
                    divisionFiltered.add(s);
                }
            }
            divisionComboBox.setItems(divisionFiltered);
        }
        else if(sentCustomerCountry.getCountryId() == 3) {
            divisionFiltered.clear();
            divisionComboBox.setItems(divisionFiltered);
            for(Division s : DBDivision.getAllDivisions()) {
                if(s.getCountryId() == 3) {
                    divisionFiltered.add(s);
                }
            }
            divisionComboBox.setItems(divisionFiltered);
        }

    }

    /**
     * Saves the customer being modified when the Save button is clicked.
     * All customer changes are updated in the database.
     * Has a conditional statement that prevents any empty fields from being saved.
     * @param event
     * @throws IOException
     */
    @FXML
    public void saveUpdateCustomer(ActionEvent event) throws IOException {
        if(customerName.getText().isBlank() || address.getText().isBlank() || postalCode.getText().isBlank() || phoneNumber.getText().isBlank() || divisionComboBox.getValue() == null) {
            Alert emptyFields = new Alert(Alert.AlertType.ERROR);
            emptyFields.setTitle("Empty field(s)");
            emptyFields.setContentText("Fill out all field(s)");
            emptyFields.showAndWait();
        }
        else {
            DBCustomers.updateCustomer(sentCustomer.getCustomerId(), customerName.getText(), address.getText(), postalCode.getText(), phoneNumber.getText(), divisionComboBox.getSelectionModel().getSelectedItem().getDivisionId());

            Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerApplicationMenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 890, 540);
            //stage.setTitle("From update customer to customer");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Cancels the customer being modified using the Cancel button.
     * @param event
     * @throws IOException
     */
    @FXML
    public void cancelUpdateCustomer(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerApplicationMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 890, 540);
        //stage.setTitle("From update customer to customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Fills and sets the division combobox given the selection of the country from the user.
     * There are conditional statements that check the country selected and sets the division combobox
     * with the respective divisions for the country.
     * @param event
     */
    @FXML
    public void onComboSelectCountry(ActionEvent event) {

        Country getItemSelected = countryComboBox.getSelectionModel().getSelectedItem();
        if(getItemSelected.getCountryId() == 1 && getItemSelected != null) {
            //Clears out observablelist of states
            divisionFiltered.clear();
            //Sets combobox with cleared out observablelist of states
            //This is so the same states are not added again
            divisionComboBox.setItems(divisionFiltered);
            for(Division s : DBDivision.getAllDivisions()) {
                if(s.getCountryId() == 1) {
                    divisionFiltered.add(s);
                }
            }
            divisionComboBox.setItems(divisionFiltered);
        }

        else if(getItemSelected.getCountryId() == 2) {
            divisionFiltered.clear();
            divisionComboBox.setItems(divisionFiltered);
            for(Division s : DBDivision.getAllDivisions()) {
                if(s.getCountryId() == 2) {
                    divisionFiltered.add(s);
                }
            }
            divisionComboBox.setItems(divisionFiltered);
        }
        else if(getItemSelected.getCountryId() == 3) {
            divisionFiltered.clear();
            divisionComboBox.setItems(divisionFiltered);
            for(Division s : DBDivision.getAllDivisions()) {
                if(s.getCountryId() == 3) {
                    divisionFiltered.add(s);
                }
            }
            divisionComboBox.setItems(divisionFiltered);
        }
    }

    /**
     * No use. Should delete probably
     * @param event
     */
    @FXML
    public void onComboSelectState(ActionEvent event) {

    }
}
