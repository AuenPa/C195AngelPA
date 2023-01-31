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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.User;
import util.DBAppointments;
import util.DBUsers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class UserController implements Initializable {

    @FXML
    private TextField passWord;

    @FXML
    private TextField userName;

    @FXML
    private Label localZone;

    private ObservableList<Appointment> listOfAppointmentsToday = FXCollections.observableArrayList();

    @FXML
    public void loginToAppointments(ActionEvent event) throws IOException {

        ObservableList<User> userList = DBUsers.getAllUsers();
        int counter = 0;
        for(User u : userList) {
            if(u.getUserName().equals(userName.getText())
            && u.getPassword().equals(passWord.getText()) ) {


                for(Appointment a : DBAppointments.getAllAppointments()) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm:ss");

                    LocalDateTime startLDT = LocalDateTime.of(a.getStartDate(), a.getStartTimeT());
                    LocalDateTime currentLDT = LocalDateTime.now();
                    long LDTDifference = ChronoUnit.MINUTES.between(currentLDT, startLDT);
                    LocalDate appDate = a.getStartDate();
                    LocalDate todaysDate = LocalDate.now();
                    Alert appointmentUpcoming = new Alert(Alert.AlertType.INFORMATION);
                    System.out.println(LocalTime.now());
                    if(LDTDifference > 0 && LDTDifference <= 15) {
                        appointmentUpcoming.setTitle("Appointment Coming up");
                        appointmentUpcoming.setContentText("You have an appointment in " + LDTDifference + " minute(s)\nAppointment ID: " + a.getAppointmentId() + "\nDate: " + a.getStartDate() + "\nTime: " + a.getStartTimeT());
                        appointmentUpcoming.showAndWait();

                    }


                    //&& appDate.equals(todaysDate)
                    //LDTDifference <= 0 && appDate.equals(todaysDate)

                    else if( numberOfAppsNotToday() > 0 || LDTDifference <= 0) {
                        /*
                        Alert appPastDue = new Alert(Alert.AlertType.INFORMATION);
                        appPastDue.setTitle("Appointment past due");
                        appPastDue.setContentText("Appointment started " + LDTDifference * -1 + " minutes ago\nAppointment ID: " + a.getAppointmentId());
                        appPastDue.showAndWait();

                         */
                        Alert noAppToday = new Alert(Alert.AlertType.INFORMATION);

                        noAppToday.setTitle("Pending Appointments");
                        noAppToday.setHeaderText("No upcoming appointments");
                        noAppToday.setContentText("You have no appointments within the next 15 minutes");
                        noAppToday.showAndWait();
                        //break;
                    }
                    /*
                    else {
                        appointmentUpcoming.setTitle("Pending Appointments");
                        appointmentUpcoming.setHeaderText("No upcoming appointments");
                        appointmentUpcoming.setContentText("You have no appointments within the next 15 minutes");
                        //break;
                    }
                     */
                }
/*
                if(numberOfAppsNotToday() > 0) {
                    Alert noAppToday = new Alert(Alert.AlertType.INFORMATION);

                    noAppToday.setTitle("Pending Appointments");
                    noAppToday.setHeaderText("No upcoming appointments");
                    noAppToday.setContentText("You have no appointments within the next 15 minutes");
                    noAppToday.showAndWait();
                }

 */
                System.out.println("Successful login!");

                Parent root = FXMLLoader.load(getClass().getResource("../view/AppointmentsMenu.fxml"));
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1132, 558);
                stage.setTitle("Customer/Appointment Form");
                stage.setScene(scene);
                stage.show();
                counter++;
            }
        }
        if(counter == 2) {
            System.out.println("Wrong username or password");
            //counter = 0;
        }
        counter = 0;

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId localZoneID = ZoneId.of(TimeZone.getDefault().getID());
        System.out.println(localZoneID);
        String zoneString = localZoneID.toString();
        localZone.setText(zoneString);
    }

    public int numberOfAppsNotToday() {
        LocalDate todaysDate = LocalDate.now();
        for(Appointment a : DBAppointments.getAllAppointments()) {
            if( !(a.getStartDate().equals(todaysDate)) ) {
                listOfAppointmentsToday.add(a);
            }
        }
        int finalSay = listOfAppointmentsToday.size();
        return finalSay;
    }
}
