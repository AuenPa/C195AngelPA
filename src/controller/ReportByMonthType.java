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
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import util.DBAppointments;

import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Responsible for displaying the number of appointments that match the month and type given.
 */
public class ReportByMonthType implements Initializable {

    /**
     * Label used to display the number of appointments matching the criteria given.
     */
    @FXML
    private Label numberOfAppointmentsLabel;

    /**
     * ComboBox used to select the month.
     */
    @FXML
    private ComboBox<Month> monthCB;

    /**
     * TextField used to enter the type of the appointment.
     */
    @FXML
    private TextField searchTypeTF;

    /**
     * Used to hold the number of appointments that match the criteria.
     */
    private int counter = 0;


    /**
     * Sets the month ComboBox with all of the months of the year.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Month> monthList = FXCollections.observableArrayList();
        monthList.addAll(Arrays.asList(Month.values()));
        monthCB.setItems(monthList);
    }

    /**
     * Switches to the all appointments table.
     */
    @FXML
    public void returnToAppointments(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/AppointmentsMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1132, 558);
        //stage.setTitle("Customer/Appointment Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Counts up the number of appointments that match the month and type selected from the ComboBox.
     * The sum is then displayed on a label.
     */
    @FXML
    public void lookUpAppointments(ActionEvent event) {
        counter = 0;
        for(Appointment a : DBAppointments.getAllAppointments()) {
            if( a.getStartDate().getMonth().equals(monthCB.getValue()) && a.getType().equals(searchTypeTF.getText()) ) {
                counter++;
            }
        }
        numberOfAppointmentsLabel.setText(String.valueOf(counter));
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

    /**
     * Switches to the report of customers by country and division screen.
     */
    @FXML
    public void toggleToReports3(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Report3.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        //stage.setTitle("From appointment menu to update appointment");
        stage.setScene(scene);
        stage.show();
    }

}
