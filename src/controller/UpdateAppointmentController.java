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
import util.DBAppointments;
import util.DBContacts;
import util.DBCustomers;
import util.DBUsers;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class UpdateAppointmentController implements Initializable {


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

    private static Appointment appToModify;

    public static void passAppointment (Appointment appointment) {
        appToModify = appointment;
    }

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

        appointmentID.setText(String.valueOf(appToModify.getAppointmentId()));
        titleTF.setText(appToModify.getTitle());
        descriptionTF.setText(appToModify.getDescription());
        locationTF.setText(appToModify.getLocation());
        typeTF.setText(appToModify.getType());
        startTimeCB.setValue(appToModify.getStartTimeT());
        endTimeCB.setValue(appToModify.getEndTimeT());
        startDatePicker.setValue(appToModify.getStartDate());
        customerIdCB.setValue(appToModify.getAssocCustomerId());
        userIdCB.setValue(appToModify.getUserId());
        contactNameComboBox.setValue(appToModify.getContactName());
    }

    @FXML
    public void cancelModifyAppointment(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1132, 558);
        stage.setTitle("From update app to appointments");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void saveModifyAppointment(ActionEvent event) throws IOException {

        String addTitle = titleTF.getText();
        String addDescription = descriptionTF.getText();
        String addLocation = locationTF.getText();
        String addType = typeTF.getText();
        LocalDateTime addStartCombine = LocalDateTime.of(startDatePicker.getValue(), startTimeCB.getValue());
        boolean checkStart = businessHoursCompare(addStartCombine);
        Timestamp addLDTConvertStart = Timestamp.valueOf(addStartCombine);
        LocalDateTime addEndCombine = LocalDateTime.of(startDatePicker.getValue(), endTimeCB.getValue());
        boolean checkEnd = businessHoursCompare(addEndCombine);
        Timestamp addLDTConvertEnd = Timestamp.valueOf(addEndCombine);
        int addAssocCustomerId = customerIdCB.getValue();
        int addUserId = userIdCB.getValue();
        for(Contact c : DBContacts.getAllContacts()){
            if(c.getContactName().equals(contactNameComboBox.getValue())){
                getContactId = c.getContactId();
                break;
            }
        }

        ZoneId eastT = ZoneId.of("America/New_York");
        ZoneId localTZone = ZoneId.systemDefault();

        ZonedDateTime currentLocalTime = addStartCombine.atZone(localTZone);
        ZonedDateTime currentEasternTime = currentLocalTime.withZoneSameInstant(eastT);
        LocalDateTime zonedEastToLDT = currentEasternTime.toLocalDateTime();

        ZonedDateTime currentLocalTimeE = addEndCombine.atZone(localTZone);
        ZonedDateTime currentEasternTimeE = currentLocalTimeE.withZoneSameInstant(eastT);
        LocalDateTime zonedEastToLDTE = currentEasternTimeE.toLocalDateTime();


        if( !(checkStart && checkEnd) ) {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm:ss");
            Alert outsideHours = new Alert(Alert.AlertType.ERROR);
            outsideHours.setTitle("Invalid Appointment Hours");
            String SB = "Business hours between 8am - 10pm est\n" +
                    "\nYour start time in est is " + zonedEastToLDT +
                    "\nYour end time in est is " + zonedEastToLDTE +
                    "\nYour current time is " + dtf.format(LocalTime.now()) +
                    "\nEST is " + dtf.format(LocalTime.ofInstant(Instant.now(), ZoneId.of("America/New_York")));
            outsideHours.setContentText(SB);
            outsideHours.showAndWait();
        }
        else if(startTimeCB.getValue().isAfter(endTimeCB.getValue()) || startTimeCB.getValue().equals(endTimeCB.getValue())) {
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
        else if(startDatePicker.getValue() == null || startTimeCB.getValue() == null || endTimeCB.getValue() == null || contactNameComboBox.getValue().isBlank()
                || customerIdCB.getValue() == null || userIdCB.getValue() == null || typeTF.getText().isBlank() || titleTF.getText().isBlank()
                || descriptionTF.getText().isBlank() || locationTF.getText().isBlank()) {
            Alert emptyFields = new Alert(Alert.AlertType.ERROR);
            emptyFields.setTitle("Empty field(s)");
            emptyFields.setContentText("Fill out all field(s)");
            emptyFields.showAndWait();
        }
        else {
            DBAppointments.updateAppointment(appToModify.getAppointmentId(), addTitle, addDescription, addLocation, addType, addLDTConvertStart, addLDTConvertEnd, addAssocCustomerId, getContactId, addUserId);

            Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsMenu.fxml"));
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1259, 558);
            stage.setTitle("From customer to appointments");
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
            if(a.getAssocCustomerId() == customerIdCB.getValue()) {
                if (a.getAppointmentId() != appToModify.getAppointmentId()) {
                    LocalDateTime addStartLDT = LocalDateTime.of(startDatePicker.getValue(), startTimeCB.getValue());
                    LocalDateTime addEndLDT = LocalDateTime.of(startDatePicker.getValue(), endTimeCB.getValue());
                    LocalDateTime checkStartLDT = LocalDateTime.of(a.getStartDate(), a.getStartTimeT());
                    LocalDateTime checkEndLDT = LocalDateTime.of(a.getEndDate(), a.getEndTimeT());
                    if ((addStartLDT.isAfter(checkStartLDT) || addStartLDT.equals(checkStartLDT))
                            && addStartLDT.isBefore(checkEndLDT)) {
                        Alert timeConflictAlert = new Alert(Alert.AlertType.ERROR);
                        timeConflictAlert.setTitle("Time conflicts with another appointment");
                        timeConflictAlert.setContentText("New appointment conflicts with " + a.getTitle() + " Appointment ID: " + a.getAppointmentId() + "Start is in window of other app");
                        timeConflictAlert.showAndWait();
                        return false;
                    } else if (addEndLDT.isAfter(checkStartLDT)
                            && (addEndLDT.isBefore(checkEndLDT) || addEndLDT.equals(checkEndLDT))) {
                        Alert timeConflictAlert = new Alert(Alert.AlertType.ERROR);
                        timeConflictAlert.setTitle("Time conflicts with another appointment");
                        timeConflictAlert.setContentText("New appointment conflicts with " + a.getTitle() + " Appointment ID: " + a.getAppointmentId() + "End is in window of other app");
                        timeConflictAlert.showAndWait();
                        return false;
                    } else if ((addStartLDT.isBefore(checkStartLDT) || addStartLDT.equals(checkStartLDT))
                            && (addEndLDT.isAfter(checkEndLDT) || addEndLDT.equals(checkEndLDT))) {
                        Alert timeConflictAlert = new Alert(Alert.AlertType.ERROR);
                        timeConflictAlert.setTitle("Time conflicts with another appointment");
                        timeConflictAlert.setContentText("New appointment conflicts with " + a.getTitle() + " Appointment ID: " + a.getAppointmentId() + "Start and end outside of window of other app or same time");
                        timeConflictAlert.showAndWait();
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
