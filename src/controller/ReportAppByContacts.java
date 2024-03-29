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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import util.DBAppointments;
import util.DBContacts;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Responsible for displaying the appointments that pertain to a specific customer.
 */
public class ReportAppByContacts implements Initializable {

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;

    @FXML
    private RadioButton appointmentMonthRB;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private ComboBox<String> contactComboBox;

    @FXML
    private TableColumn<Appointment, Integer> contactNameCol;

    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, LocalTime> endTimeCol;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private TableColumn<Appointment, LocalDate> startDateCol;

    @FXML
    private TableColumn<Appointment, LocalTime> startTimeCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, Integer> userIdCol;

    /**
     * Allows the user to logout when clicked.
     * This returns to the login screen.
     */
    @FXML
    public void logout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Logout?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/User.fxml"));
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 743, 526);
            //stage.setTitle("Logged out");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Switches to the all appointments screen.
     */
    @FXML
    public void toggleToAllAppo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1132, 558);
        //stage.setTitle("From appointments by month to all apps");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches to appointments by month screen.
     */
    @FXML
    public void toggleToAppoMonth(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsByMonth.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1132, 558);
        //stage.setTitle("From appointments to apps by month");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches to appointments by week screen.
     */
    @FXML
    public void toggleToAppoWeek(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentByWeek.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1132, 558);
        //stage.setTitle("From appointments to apps by week");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches to the customer table screen.
     */
    @FXML
    public void toggleToCustomer(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerApplicationMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 890, 558);
        //stage.setTitle("From appointments to customer");
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

    /**
     * Filters through the appointments that have the contact name selected by the user.
     * Sets the appointments in the table.
     */
    @FXML
    public void searchForAppointments(ActionEvent event) {

        ObservableList<Appointment> appByContactList = FXCollections.observableArrayList();
        for(Appointment a : DBAppointments.getAllAppointments()) {
            if(a.getContactName().equals(contactComboBox.getValue()) ) {
                appByContactList.add(a);
            }
        }
        appointmentTable.setItems(appByContactList);

    }
/*
    @FXML
    public void lookUpAppsByContacts(ActionEvent event) {
        ObservableList<Appointment> appByContactList = FXCollections.observableArrayList();
        for(Appointment a : DBAppointments.getAllAppointments()) {
            if(a.getContactName().equals(contactComboBox.getSelectionModel().getSelectedItem()) ) {
                appByContactList.add(a);
            }
        }
        appointmentTable.setItems(appByContactList);

        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        contactNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("assocCustomerId"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTimeT"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTimeT"));

    }
 */

    /**
     * Populates the contact ComboBox with the contacts from the database.
     * Primes the table to be populated with the correct values.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        for(Contact c : DBContacts.getAllContacts()){
            contactNames.add(c.getContactName());
        }
        contactComboBox.setItems(contactNames);

        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        contactNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("assocCustomerId"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTimeT"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTimeT"));
    }
}
