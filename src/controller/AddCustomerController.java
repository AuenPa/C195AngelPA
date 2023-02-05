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
import model.Division;
import util.DBCountry;
import util.DBCustomers;
import util.DBDivision;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is responsible for adding customers. The new customers instances are added to a database.
 */
public class AddCustomerController implements Initializable {

    /**
     * TextField for inputting the address for the customer.
     */
    @FXML
    private TextField address;

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
     * Used to hold the divisions filtered by their respective country.
     */
    private ObservableList<Division> divisionFiltered = FXCollections.observableArrayList();

    /**
     * Cancels the customer being added using the Cancel button.
     */
    @FXML
    public void cancelAddCustomer(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerApplicationMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 890, 540);
        //stage.setTitle("From add customer to customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Saves the customer being added when the Save button is clicked.
     * All customer instances are put into the database.
     * Has a conditional statement that prevents any empty fields from being saved.
     */
    @FXML
    public void saveAddCustomer(ActionEvent event) throws IOException {
        if(customerName.getText().isBlank() || address.getText().isBlank() || postalCode.getText().isBlank() || phoneNumber.getText().isBlank() || divisionComboBox.getValue() == null) {
            Alert emptyFields = new Alert(Alert.AlertType.ERROR);
            emptyFields.setTitle("Empty field(s)");
            emptyFields.setContentText("Fill out all field(s)");
            emptyFields.showAndWait();
        }
        else {
            DBCustomers.addCustomer(customerName.getText(), address.getText(), postalCode.getText(),
                    phoneNumber.getText(), divisionComboBox.getSelectionModel().getSelectedItem().getDivisionId());

            Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerApplicationMenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 890, 540);
            //stage.setTitle("From add customer to customer");
            stage.setScene(scene);
            stage.show();
        }

    }

    /**
     * Used to fill and set the country combobox with all of the countries from the database.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Country> countries = DBCountry.getAllCountries();
        countryComboBox.setItems(countries);

    }

    /**
     * Fills and sets the division combobox given the selection of the country from the user.
     * There are conditional statements that check the country selected and sets the division combobox
     * with the respective divisions for the country.
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

}
