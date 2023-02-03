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

public class AddAppointmentController implements Initializable {


    @FXML
    private TextField appointmentID;

    @FXML
    private ComboBox<String> contactNameComboBox;

    @FXML
    private ComboBox<Integer> customerIdCB;

    @FXML
    private TextField descriptionTF;

    @FXML
    private ComboBox<LocalTime> endTimeCB;

    @FXML
    private TextField locationTF;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private ComboBox<LocalTime> startTimeCB;

    @FXML
    private TextField titleTF;

    @FXML
    private TextField typeTF;

    @FXML
    private ComboBox<Integer> userIdCB;

    private int getContactId;

    private LocalTime addLocalTimes = LocalTime.of(4, 0);


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

    @FXML
    public void cancelAddAppointment(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1132, 558);
        //stage.setTitle("From add app to appointments");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void saveAddAppointment(ActionEvent event) throws IOException {


        String addTitle = titleTF.getText();
        String addDescription = descriptionTF.getText();
        String addLocation = locationTF.getText();
        String addType = typeTF.getText();
/*
        LocalDate dateChosen = startDatePicker.getValue();
        LocalTime startTimeChosen = startTimeCB.getValue();
        LocalDateTime addStartCombine = LocalDateTime.of(startDatePicker.getValue(), startTimeCB.getValue());
        //boolean checkStart = businessHoursCompare(addStartCombine);
        Timestamp addLDTConvertStart = Timestamp.valueOf(LocalDateTime.of(startDatePicker.getValue(), startTimeCB.getValue()));
        businessHoursCompare(LocalDateTime.of(startDatePicker.getValue(), startTimeCB.getValue()));

        LocalTime endTimeChosen = endTimeCB.getValue();
        LocalDateTime addEndCombine = LocalDateTime.of(startDatePicker.getValue(), endTimeCB.getValue());
        //boolean checkEnd = businessHoursCompare(addEndCombine);
        Timestamp addLDTConvertEnd = Timestamp.valueOf(LocalDateTime.of(startDatePicker.getValue(), endTimeCB.getValue()));
 */
        //int addAssocCustomerId = customerIdCB.getValue();
        //int addUserId = userIdCB.getValue();

        for (Contact c : DBContacts.getAllContacts()) {
            AddAppointmentInterface contactCheck = s -> c.getContactName().equals(s);
            //c.getContactName().equals(contactNameComboBox.getValue())
            if (contactCheck.checkContact(contactNameComboBox.getValue())) {
                getContactId = c.getContactId();
                break;
            }
        }
/*
        ZoneId eastT = ZoneId.of("America/New_York");
        ZoneId localTZone = ZoneId.systemDefault();

        ZonedDateTime currentLocalTime = addStartCombine.atZone(localTZone);
        ZonedDateTime currentEasternTime = currentLocalTime.withZoneSameInstant(eastT);
        LocalDateTime zonedEastToLDT = currentEasternTime.toLocalDateTime();

        ZonedDateTime currentLocalTimeE = addEndCombine.atZone(localTZone);
        ZonedDateTime currentEasternTimeE = currentLocalTimeE.withZoneSameInstant(eastT);
        LocalDateTime zonedEastToLDTE = currentEasternTimeE.toLocalDateTime();


 */

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

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm:ss");
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

    public static boolean businessHoursCompare(LocalDateTime timeToCheck) {
        ZoneId eastT = ZoneId.of("America/New_York");
        ZoneId localTZone = ZoneId.systemDefault();
        ZonedDateTime currentLocalTime = timeToCheck.atZone(localTZone);
        ZonedDateTime currentEasternTime = currentLocalTime.withZoneSameInstant(eastT);

        LocalDateTime zonedEastToLDT = currentEasternTime.toLocalDateTime();

        ZonedDateTime eastStart = ZonedDateTime.of(currentEasternTime.toLocalDate(), LocalTime.of(8,0), eastT);
        //System.out.println("eastern zoned start time" + eastStart + " " + eastStart.toLocalDateTime());

        ZonedDateTime eastEnd = ZonedDateTime.of(currentEasternTime.toLocalDate(), LocalTime.of(22,0), eastT);
        //System.out.println("eastern zoned end time " + eastEnd + " " + eastEnd.toLocalDateTime());

        return ( zonedEastToLDT.isAfter(ChronoLocalDateTime.from(eastStart)) || zonedEastToLDT.equals(ChronoLocalDateTime.from(eastStart)) )
                && ( zonedEastToLDT.isBefore(ChronoLocalDateTime.from(eastEnd)) || zonedEastToLDT.equals(ChronoLocalDateTime.from(eastEnd)));
    }

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
