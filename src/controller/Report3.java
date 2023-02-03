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

public class Report3 implements Initializable {

    @FXML
    private ComboBox<Division> DivisionCB;

    @FXML
    private ComboBox<Country> countryCB;

    @FXML
    private Label numberOfCustomersLabel;

    private ObservableList<Division> divisionFiltered = FXCollections.observableArrayList();

    private int counter = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Country> countries = DBCountry.getAllCountries();
        countryCB.setItems(countries);
    }

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

    @FXML
    public void returnToAppointments(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1132, 558);
        //stage.setTitle("From appointments by month to all apps");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void toggleToReports1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportByMonth_Type.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        //stage.setTitle("From appointment menu to update appointment");
        stage.setScene(scene);
        stage.show();
    }

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
