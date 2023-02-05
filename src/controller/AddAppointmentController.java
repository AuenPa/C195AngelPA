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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import util.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

/**
 * This class is responsible for adding appointments. The new appointment instances are added to a database.
 */
public class AddAppointmentController implements Initializable {

    /**
     * ComboBox for appointment contacts.
     */
    @FXML
    private ComboBox<String> contactNameComboBox;

    /**
     * ComboBox for appointment customer IDs.
     */
    @FXML
    private ComboBox<Integer> customerIdCB;

    /**
     * TextField for the description of the appointment.
     */
    @FXML
    private TextField descriptionTF;

    /**
     * ComboBox for the appointments end time.
     */
    @FXML
    private ComboBox<LocalTime> endTimeCB;

    /**
     * TextField for the appointments location.
     */
    @FXML
    private TextField locationTF;

    /**
     * DatePicker for the appointment start and end date.
     */
    @FXML
    private DatePicker startDatePicker;

    /**
     * ComboBox for the appointments start time.
     */
    @FXML
    private ComboBox<LocalTime> startTimeCB;

    /**
     * TextField for the appointments title.
     */
    @FXML
    private TextField titleTF;

    /**
     * TextField for the type of appointment
     */
    @FXML
    private TextField typeTF;

    /**
     * ComboBox for choosing the user ID for the appointment.
     */
    @FXML
    private ComboBox<Integer> userIdCB;

    /**
     * Used to add the contact ID to the database when it is chosen from the contact contact name combobox.
     */
    private int getContactId;

    /**
     * Used to fill the start and end time comboboxes.
     */
    private LocalTime addLocalTimes = LocalTime.of(4, 0);

    /**
     * Sets the comboboxes for contact names, start and end times, customer and user IDs for selection.
     * @param url No usage for this method
     * @param resourceBundle No usage for this method
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        for(Contact c : DBContacts.getAllContacts()){
            contactNames.add(c.getContactName());
        }
        contactNameComboBox.setItems(contactNames);

        ObservableList<LocalTime> listOfTimes = FXCollections.observableArrayList();
        for(int i = 0; i <= 1140; i+=15) {
            listOfTimes.add(addLocalTimes.plusMinutes(i));
        }
        startTimeCB.setItems(listOfTimes);
        endTimeCB.setItems(listOfTimes);

        ObservableList<Integer> customerIds = FXCollections.observableArrayList();
        for(Customer c : DBCustomers.getAllCustomers()) {
            customerIds.add(c.getCustomerId());
        }
        customerIdCB.setItems(customerIds);

        ObservableList<Integer> userIds = FXCollections.observableArrayList();
        for(User u : DBUsers.getAllUsers()) {
            userIds.add(u.getUserId());
        }
        userIdCB.setItems(userIds);
    }

    /**
     * Cancels the scheduling of the appointment when Cancel is clicked.
     * @param event not used
     * @throws IOException
     */
    @FXML
    public void cancelAddAppointment(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1132, 558);
        //stage.setTitle("From add app to appointments");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Saves the appointment when Save is clicked.
     * All appointment instances are put into the database.
     * Uses other methods to ensure the data being saved is valid, sends an alert indicating if otherwise.
     * <p>
     * The lambda expression, contactCheck, is used to check if the contact name selected in the
     * contact name ComboBox is equal to a contact in the database. The ID of the contact is stored
     * when the names match and is used as an attribute to be stored in the new instance.
     * </p>
     * @param event no use
     * @throws IOException
     */
    @FXML
    public void saveAddAppointment(ActionEvent event) throws IOException {

        String addTitle = titleTF.getText();
        String addDescription = descriptionTF.getText();
        String addLocation = locationTF.getText();
        String addType = typeTF.getText();

        //used to check the contact name selected from the combobox and sets getContactId.
        //When the selected name equals the name from the list of all contacts, it gets the contact ID from the
        //contact that matches and is sent to be saved.
        for (Contact c : DBContacts.getAllContacts()) {
            AddAppointmentInterface contactCheck = s -> c.getContactName().equals(s);
            //c.getContactName().equals(contactNameComboBox.getValue())
            if (contactCheck.checkContact(contactNameComboBox.getValue())) {
                getContactId = c.getContactId();
                break;
            }
        }

        //Conditional statements to ensure the data provided is valid and there are no empty TextFields or CombobBoxes
        if( (startDatePicker.getValue() != null && startTimeCB.getValue() != null && endTimeCB.getValue() != null)
                && !( businessHoursCompare(LocalDateTime.of(startDatePicker.getValue(), startTimeCB.getValue())) && businessHoursCompare(LocalDateTime.of(startDatePicker.getValue(), endTimeCB.getValue()))
) ) {

            ZoneId eastT = ZoneId.of("America/New_York");
            ZoneId localTZone = ZoneId.systemDefault();

            ZonedDateTime currentLocalTime = LocalDateTime.of(startDatePicker.getValue(), startTimeCB.getValue()).atZone(localTZone);
            ZonedDateTime currentEasternTime = currentLocalTime.withZoneSameInstant(eastT);
            LocalDateTime zonedEastToLDT = currentEasternTime.toLocalDateTime();
            LocalTime easternTimeFormat = LocalTime.from(zonedEastToLDT);

            ZonedDateTime currentLocalTimeE = LocalDateTime.of(startDatePicker.getValue(), endTimeCB.getValue()).atZone(localTZone);
            ZonedDateTime currentEasternTimeE = currentLocalTimeE.withZoneSameInstant(eastT);
            LocalDateTime zonedEastToLDTE = currentEasternTimeE.toLocalDateTime();
            LocalTime easternTimeFormatEnd = LocalTime.from(zonedEastToLDTE);

            DateTimeFormatter testDTF = DateTimeFormatter.ofLocalizedTime(FormatStyle.LONG).withZone(ZoneId.systemDefault());
            DateTimeFormatter easternDTF = DateTimeFormatter.ofLocalizedTime(FormatStyle.LONG).withZone(eastT);

            Alert outsideHours = new Alert(Alert.AlertType.ERROR);
            outsideHours.setTitle("Invalid Appointment Hours");
            String SB = "Business hours between 8am - 10pm est\n" +
                    "\nYour start time in est is " + easternDTF.format(easternTimeFormat) +
                    "\nYour end time in est is " + easternDTF.format(easternTimeFormatEnd) +
                    "\nYour current time is " + testDTF.format(LocalTime.now()) +
                    "\nEST is " + easternDTF.format(LocalTime.ofInstant(Instant.now(), ZoneId.of("America/New_York")));
            outsideHours.setContentText(SB);
            outsideHours.showAndWait();
        }
        else if( (startDatePicker.getValue() != null && startTimeCB.getValue() != null && endTimeCB.getValue() != null) && (startTimeCB.getValue().isAfter(endTimeCB.getValue()) || startTimeCB.getValue().equals(endTimeCB.getValue())) ){
            Alert startTimeError = new Alert(Alert.AlertType.ERROR);
            startTimeError.setTitle("Start Time Selection Error");
            startTimeError.setContentText("Start time has to be before end time");
            startTimeError.showAndWait();
        }
        else if(!timeConflicts()) {
            System.out.println("there were appointments that conflicted with others in terms of time");
            LocalDateTime potentialAppointmentTime = LocalDateTime.of(startDatePicker.getValue(), startTimeCB.getValue());
            LocalTime pAT = LocalTime.from(potentialAppointmentTime);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm:ss");
            System.out.println("Potential app start time: " + dtf.format(pAT));
        }
        else if(startDatePicker.getValue() == null || startTimeCB.getValue() == null || endTimeCB.getValue() == null || contactNameComboBox.getValue() == null
        || customerIdCB.getValue() == null || userIdCB.getValue() == null || typeTF.getText().isBlank() || titleTF.getText().isBlank()
        || descriptionTF.getText().isBlank() || locationTF.getText().isBlank()) {
            Alert emptyFields = new Alert(Alert.AlertType.ERROR);
            emptyFields.setTitle("Empty field(s)");
            emptyFields.setContentText("Fill out all field(s)");
            emptyFields.show();
        }
        else {
            DBAppointments.addAppointment(addTitle, addDescription, addLocation, addType, Timestamp.valueOf(LocalDateTime.of(startDatePicker.getValue(), startTimeCB.getValue())), Timestamp.valueOf(LocalDateTime.of(startDatePicker.getValue(), endTimeCB.getValue())), customerIdCB.getValue(), getContactId, userIdCB.getValue());

            Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsMenu.fxml"));
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1259, 558);
            //stage.setTitle("From customer to appointments");
            stage.setScene(scene);
            stage.show();
        }

    }

    /**
     * Takes the times selected and returns false if the hours are outside of business hours.
     * Using eastern time, the business hours are set from 8am - 10 pm and checks if the time chosen for the
     * appointment is in this time block.
     * @param timeToCheck this will be the start and/or end time chosen
     * @return false or true if the hours selected are within business hours
     */
    public static boolean businessHoursCompare(LocalDateTime timeToCheck) {
        ZoneId eastT = ZoneId.of("America/New_York");
        ZoneId localTZone = ZoneId.systemDefault();
        ZonedDateTime currentLocalTime = timeToCheck.atZone(localTZone);
        ZonedDateTime currentEasternTime = currentLocalTime.withZoneSameInstant(eastT);

        LocalDateTime zonedEastToLDT = currentEasternTime.toLocalDateTime();

        ZonedDateTime eastStart = ZonedDateTime.of(currentEasternTime.toLocalDate(), LocalTime.of(8,0), eastT);

        ZonedDateTime eastEnd = ZonedDateTime.of(currentEasternTime.toLocalDate(), LocalTime.of(22,0), eastT);

        return ( zonedEastToLDT.isAfter(ChronoLocalDateTime.from(eastStart)) || zonedEastToLDT.equals(ChronoLocalDateTime.from(eastStart)) )
                && ( zonedEastToLDT.isBefore(ChronoLocalDateTime.from(eastEnd)) || zonedEastToLDT.equals(ChronoLocalDateTime.from(eastEnd)));
    }

    /**
     * Checks if the time selected is in conflict with another appointment for the same customer.
     * Checks whether the start time is within the time window of another appointment.
     * Also checks if the end time is within the time window of another appointment.
     * And checks if the new appointment time is equal to or is greater in time to another appointment listed.
     * @return true or false if the time conflicts with another appointment time window
     */
    public boolean timeConflicts() {
        for(Appointment a : DBAppointments.getAllAppointments()) {
            if(customerIdCB.getValue() != null && a.getAssocCustomerId() == customerIdCB.getValue()) {
                LocalDateTime addStartLDT = LocalDateTime.of(startDatePicker.getValue(), startTimeCB.getValue());
                LocalDateTime addEndLDT = LocalDateTime.of(startDatePicker.getValue(), endTimeCB.getValue());
                LocalDateTime checkStartLDT = LocalDateTime.of(a.getStartDate(), a.getStartTimeT());
                LocalDateTime checkEndLDT = LocalDateTime.of(a.getEndDate(), a.getEndTimeT());
                if( ( addStartLDT.isAfter(checkStartLDT) || addStartLDT.equals(checkStartLDT) )
                 &&  addStartLDT.isBefore(checkEndLDT) ) {
                    Alert timeConflictAlert = new Alert(Alert.AlertType.ERROR);
                    timeConflictAlert.setTitle("Time conflicts with another appointment");
                    timeConflictAlert.setContentText("New appointment conflicts with " + a.getTitle() + " Appointment ID: " + a.getAppointmentId() + "Start is in window of other app");
                    timeConflictAlert.showAndWait();
                    return false;
                }
                else if( addEndLDT.isAfter(checkStartLDT)
                    && ( addEndLDT.isBefore(checkEndLDT) || addEndLDT.equals(checkEndLDT) ) ){
                    Alert timeConflictAlert = new Alert(Alert.AlertType.ERROR);
                    timeConflictAlert.setTitle("Time conflicts with another appointment");
                    timeConflictAlert.setContentText("New appointment conflicts with " + a.getTitle() + " Appointment ID: " + a.getAppointmentId() + "End is in window of other app");
                    timeConflictAlert.showAndWait();
                    return false;
                }
                else if( ( addStartLDT.isBefore(checkStartLDT) || addStartLDT.equals(checkStartLDT) )
                && (addEndLDT.isAfter(checkEndLDT) || addEndLDT.equals(checkEndLDT) ) ) {
                    Alert timeConflictAlert = new Alert(Alert.AlertType.ERROR);
                    timeConflictAlert.setTitle("Time conflicts with another appointment");
                    timeConflictAlert.setContentText("New appointment conflicts with " + a.getTitle() + " Appointment ID: " + a.getAppointmentId() + "Start and end outside of window of other app or same time");
                    timeConflictAlert.showAndWait();
                    return false;
                }
            }
        }
        return true;
    }
}
