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
import javafx.scene.control.Button;
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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class UserController implements Initializable {

    @FXML
    private TextField passWord;

    @FXML
    private TextField userName;

    @FXML
    private Label localZone;

    @FXML
    private Button loginButtonLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label usernameLabel;

    private ObservableList<Appointment> listOfAppointmentsToday = FXCollections.observableArrayList();

    private ResourceBundle rb = ResourceBundle.getBundle("util/Lang_fr", Locale.getDefault());


    @FXML
    public void loginToAppointments(ActionEvent event) throws IOException {

        ObservableList<User> userList = DBUsers.getAllUsers();
        System.out.println(userList.get(1).getUserName());
        int counter = 0;
        if( (userList.get(0).getUserName().equals(userName.getText())
            && userList.get(0).getPassword().equals(passWord.getText())) || (userList.get(1).getUserName().equals(userName.getText()) && userList.get(1).getPassword().equals(passWord.getText())) ) {

            int count = 0;
            for(Appointment a : DBAppointments.getAllAppointments()) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm:ss");
                LocalDateTime startLDT = LocalDateTime.of(a.getStartDate(), a.getStartTimeT());
                LocalDateTime currentLDT = LocalDateTime.now();
                long LDTDifference = ChronoUnit.MINUTES.between(currentLDT, startLDT);

                if(LDTDifference > 0 && LDTDifference <= 15) {
                    Alert appointmentUpcoming = new Alert(Alert.AlertType.INFORMATION);

                    if(Locale.getDefault().getLanguage().equals("fr")) {
                        appointmentUpcoming.setTitle(rb.getString("Appointment_Coming_Up"));
                        appointmentUpcoming.setContentText(rb.getString("appointment_in") + LDTDifference + " " + rb.getString("minsAppId") + a.getAppointmentId() + rb.getString("date") + a.getStartDate() + rb.getString("time") + a.getStartTimeT());
                        appointmentUpcoming.showAndWait();
                    }
                    else {
                        appointmentUpcoming.setTitle("Appointment Coming up");
                        appointmentUpcoming.setContentText("You have an appointment in " + LDTDifference + " minute(s)\nAppointment ID: " + a.getAppointmentId() + "\nDate: " + a.getStartDate() + "\nTime: " + a.getStartTimeT());
                        appointmentUpcoming.showAndWait();
                        count++;
                    }
                }

            }

            if( !(count > 0) ) {
                Alert noAppToday = new Alert(Alert.AlertType.INFORMATION);

                if(Locale.getDefault().getLanguage().equals("fr")) {
                    noAppToday.setTitle(rb.getString("noAppTitle"));
                    noAppToday.setHeaderText(rb.getString("noAppHT"));
                    noAppToday.setContentText(rb.getString("noAppContext"));
                }
                else {
                    noAppToday.setTitle("Pending Appointments");
                    noAppToday.setHeaderText("No upcoming appointments");
                    noAppToday.setContentText("You have no appointments within the next 15 minutes");
                    noAppToday.showAndWait();
                }
            }


            System.out.println("Successful login!");

            Parent root = FXMLLoader.load(getClass().getResource("../view/AppointmentsMenu.fxml"));
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1132, 558);
            //stage.setTitle("Customer/Appointment Form");
            stage.setScene(scene);
            stage.show();
            counter++;
        }
        else {
            Alert wrongLoginCreds = new Alert(Alert.AlertType.ERROR);

            wrongLoginCreds.setTitle("Error Login");
            wrongLoginCreds.setHeaderText("Wrong username/password");
            wrongLoginCreds.setContentText("Username or password entered incorrectly");
            wrongLoginCreds.showAndWait();
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

        //ResourceBundle rb = ResourceBundle.getBundle("util/Lang_fr", Locale.getDefault());

        if(Locale.getDefault().getLanguage().equals("fr")) {
            usernameLabel.setText(rb.getString("Username"));
            passwordLabel.setText(rb.getString("Password"));
            loginButtonLabel.setText(rb.getString("Login"));
            titleLabel.setText(rb.getString("User_Login"));
        }
    }

}
