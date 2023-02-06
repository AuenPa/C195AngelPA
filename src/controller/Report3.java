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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
 * This class is responsible for displaying the number of customer from a certain country and division.
 */
public class Report3 implements Initializable {

    /**
     * ComboBox used to select the division.
     */
    @FXML
    private ComboBox<Division> DivisionCB;

    /**
     * ComboBox used to select the country.
     */
    @FXML
    private ComboBox<Country> countryCB;

    /**
     * Label used to display the number of customer from the same division.
     */
    @FXML
    private Label numberOfCustomersLabel;

    /**
     * Used to hold the divisions that match the country selected.
     */
    private ObservableList<Division> divisionFiltered = FXCollections.observableArrayList();

    /**
     * Holds the number of customers that match the given criteria.
     */
    private int counter = 0;

    /**
     * Sets the country combobox with the countries in the database for user selection.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Country> countries = DBCountry.getAllCountries();
        countryCB.setItems(countries);
    }

    /**
     * Sets the division combobox given the selection of the country from the user.
     */
    @FXML
    public void getCountrySelect(ActionEvent event) {

        Country getItemSelected = countryCB.getSelectionModel().getSelectedItem();
        if(getItemSelected.getCountryId() == 1 && getItemSelected != null) {
            //Clears out observablelist of states
            divisionFiltered.clear();
            //Sets combobox with cleared out observablelist of states
            //This is so the same states are not added again
            DivisionCB.setItems(divisionFiltered);
            for(Division s : DBDivision.getAllDivisions()) {
                if(s.getCountryId() == 1) {
                    divisionFiltered.add(s);
                }
            }
            DivisionCB.setItems(divisionFiltered);
        }

        else if(getItemSelected.getCountryId() == 2) {
            divisionFiltered.clear();
            DivisionCB.setItems(divisionFiltered);
            for(Division s : DBDivision.getAllDivisions()) {
                if(s.getCountryId() == 2) {
                    divisionFiltered.add(s);
                }
            }
            DivisionCB.setItems(divisionFiltered);
        }
        else if(getItemSelected.getCountryId() == 3) {
            divisionFiltered.clear();
            DivisionCB.setItems(divisionFiltered);
            for(Division s : DBDivision.getAllDivisions()) {
                if(s.getCountryId() == 3) {
                    divisionFiltered.add(s);
                }
            }
            DivisionCB.setItems(divisionFiltered);
        }
    }

    /**
     * Counts the number of customers from the same division and sums them all up.
     * It then displays the number of customers that match the same division.
     */
    @FXML
    public void getDivisionSelect(ActionEvent event) {
        try {
            counter = 0;
            for (Customer c : DBCustomers.getAllCustomers()) {
                if (c.getDivision().equals(DivisionCB.getSelectionModel().getSelectedItem().getDivision())) {
                    counter++;
                }
            }
            numberOfCustomersLabel.setText(String.valueOf(counter));
        }
        catch (NullPointerException e) {
            //ignore
        }
    }

    /**
     * Switches to the all appointments table.
     */
    @FXML
    public void returnToAppointments(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1132, 558);
        //stage.setTitle("From appointments by month to all apps");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches to the report of appointments by month and type screen.
     */
    @FXML
    public void toggleToReports1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportByMonth_Type.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        //stage.setTitle("From appointment menu to update appointment");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches to the report of appointments by contacts screen.
     */
    @FXML
    public void toggleToReports2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportAppByContacts.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1132, 558);
        //stage.setTitle("From appointment menu to update appointment");
        stage.setScene(scene);
        stage.show();
    }

}
