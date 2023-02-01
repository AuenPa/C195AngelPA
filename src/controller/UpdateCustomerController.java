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

public class UpdateCustomerController implements Initializable {

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

    private static Customer sentCustomer;

    public static void passCustomer (Customer customer) {
        sentCustomer = customer;
    }

    private State sentCustomerState;

    private Country sentCustomerCountry;

    private ObservableList<State> stateFiltered = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //probably don't need this
        ObservableList<State> slist = DBState.getAllStates();
        stateComboBox.setItems(slist);

        ObservableList<Country> clist = DBCountry.getAllCountries();
        countryComboBox.setItems(clist);

        customerID.setText(String.valueOf(sentCustomer.getCustomerId()));
        customerName.setText(sentCustomer.getCustomerName());
        address.setText(sentCustomer.getAddress());
        postalCode.setText(sentCustomer.getPostalCode());
        phoneNumber.setText(sentCustomer.getPhoneNumber());
        //countryComboBox.setItems(sentCustomer.);
        int stateID = sentCustomer.getDivisionId();
        //State sentCustomerState = null;
        for(State S : DBState.getAllStates()) {
            if(S.getDivisionId() == stateID) {
                sentCustomerState = S;
                break;
            }
        }
        stateComboBox.setValue(sentCustomerState);


        for(Country C : DBCountry.getAllCountries()) {
            if(C.getCountryId() == sentCustomerState.getCountryId()) {
                sentCustomerCountry = C;
            }
        }
        countryComboBox.setValue(sentCustomerCountry);

        if(sentCustomerCountry.getCountryId() == 1) {
            stateFiltered.clear();
            stateComboBox.setItems(stateFiltered);

            for(State S : DBState.getAllStates()) {
                if(S.getCountryId() == 1) {
                    stateFiltered.add(S);
                }
            }
            stateComboBox.setItems(stateFiltered);
        }

        else if(sentCustomerCountry.getCountryId() == 2) {
            stateFiltered.clear();
            stateComboBox.setItems(stateFiltered);
            for(State s :DBState.getAllStates()) {
                if(s.getCountryId() == 2) {
                    stateFiltered.add(s);
                }
            }
            stateComboBox.setItems(stateFiltered);
        }
        else if(sentCustomerCountry.getCountryId() == 3) {
            stateFiltered.clear();
            stateComboBox.setItems(stateFiltered);
            for(State s : DBState.getAllStates()) {
                if(s.getCountryId() == 3) {
                    stateFiltered.add(s);
                }
            }
            stateComboBox.setItems(stateFiltered);
        }

    }

    @FXML
    public void saveUpdateCustomer(ActionEvent event) throws IOException {
        if(customerName.getText().isBlank() || address.getText().isBlank() || postalCode.getText().isBlank() || phoneNumber.getText().isBlank() || stateComboBox.getValue() == null) {
            Alert emptyFields = new Alert(Alert.AlertType.ERROR);
            emptyFields.setTitle("Empty field(s)");
            emptyFields.setContentText("Fill out all field(s)");
            emptyFields.showAndWait();
        }
        else {
            DBCustomers.updateCustomer(sentCustomer.getCustomerId(), customerName.getText(), address.getText(), postalCode.getText(), phoneNumber.getText(), stateComboBox.getSelectionModel().getSelectedItem().getDivisionId());

            Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerApplicationMenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 890, 540);
            //stage.setTitle("From update customer to customer");
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void cancelUpdateCustomer(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerApplicationMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 890, 540);
        //stage.setTitle("From update customer to customer");
        stage.setScene(scene);
        stage.show();
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
    }

    @FXML
    public void onComboSelectState(ActionEvent event) {

    }
}
