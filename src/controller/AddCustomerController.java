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
import model.State;
import util.DBCountry;
import util.DBCustomers;
import util.DBState;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    @FXML
    private TextField address;

    @FXML
    private TextField customerID;

    @FXML
    private TextField customerName;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField postalCode;

    @FXML
    private ComboBox<State> stateComboBox;

    @FXML
    private ComboBox<Country> countryComboBox;

    private ObservableList<State> stateFiltered = FXCollections.observableArrayList();

    @FXML
    public void cancelAddCustomer(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerApplicationMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 890, 540);
        stage.setTitle("From add customer to customer");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void saveAddCustomer(ActionEvent event) throws IOException {
        if(customerName.getText().isBlank() || address.getText().isBlank() || postalCode.getText().isBlank() || phoneNumber.getText().isBlank() || stateComboBox.getValue() == null) {
            Alert emptyFields = new Alert(Alert.AlertType.ERROR);
            emptyFields.setTitle("Empty field(s)");
            emptyFields.setContentText("Fill out all field(s)");
            emptyFields.showAndWait();
        }
        else {
            DBCustomers.addCustomer(customerName.getText(), address.getText(), postalCode.getText(),
                    phoneNumber.getText(), stateComboBox.getSelectionModel().getSelectedItem().getDivisionId());
            //Customer.addCustomerCM();

            Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerApplicationMenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 890, 540);
            stage.setTitle("From add customer to customer");
            stage.setScene(scene);
            stage.show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Country> countries = DBCountry.getAllCountries();
        countryComboBox.setItems(countries);
/*
        ObservableList<State> states = DBState.getAllStates();
        stateComboBox.setItems(states);

 */
    }

    @FXML
    public void onComboSelectCountry(ActionEvent event) {

        Country getItemSelected = countryComboBox.getSelectionModel().getSelectedItem();
        if(getItemSelected.getCountryId() == 1 && getItemSelected != null) {
            //Clears out observablelist of states
            stateFiltered.clear();
            //Sets combobox with cleared out observablelist of states
            //This is so the same states are not added again
            stateComboBox.setItems(stateFiltered);
            for(State s : DBState.getAllStates()) {
                if(s.getCountryId() == 1) {
                    stateFiltered.add(s);
                }
            }
            stateComboBox.setItems(stateFiltered);
        }

        else if(getItemSelected.getCountryId() == 2) {
            stateFiltered.clear();
            stateComboBox.setItems(stateFiltered);
            for(State s :DBState.getAllStates()) {
                if(s.getCountryId() == 2) {
                    stateFiltered.add(s);
                }
            }
            stateComboBox.setItems(stateFiltered);
        }
        else if(getItemSelected.getCountryId() == 3) {
            stateFiltered.clear();
            stateComboBox.setItems(stateFiltered);
            for(State s : DBState.getAllStates()) {
                if(s.getCountryId() == 3) {
                    stateFiltered.add(s);
                }
            }
            stateComboBox.setItems(stateFiltered);
        }

        /*
        for(Country c : DBCountry.getAllCountries()) {
            if(countryComboBox.getSelectionModel().getSelectedItem().equals(c)){
                int checkCountryId = c.getCountryId();
            }

        }

         */
    }

    @FXML
    public void onComboSelectState(ActionEvent event) {

    }

}
