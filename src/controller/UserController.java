package controller;

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

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * This class is responsible for login functions and is the initial screen when the program starts.
 */
public class UserController implements Initializable {

    /**
     * TextField for taking the password.
     */
    @FXML
    private TextField passWord;

    /**
     * TextField for taking the username.
     */
    @FXML
    private TextField userName;

    /**
     * Label for displaying the locale.
     */
    @FXML
    private Label localZone;

    /**
     * Button used to login when clicked.
     */
    @FXML
    private Button loginButtonLabel;

    /**
     * Label that says password.
     */
    @FXML
    private Label passwordLabel;

    /**
     * Title label.
     */
    @FXML
    private Label titleLabel;

    /**
     * Username label.
     */
    @FXML
    private Label usernameLabel;

    /**
     * ResourceBundle used to retrieve the key-value pair information that is required for the translation to french.
     */
    private ResourceBundle rb = ResourceBundle.getBundle("util/Lang_fr", Locale.getDefault());


    /**
     * This method allows the user to login when the valid credentials are provided.
     * Along with allowing the user to login, it also checks if there is an appointment within 15 minutes of the login.
     * It also records the user login attempts and sends that information to a text file.
     * When the users language setting is set to french, it defaults any relevant text in the login screen to french. Specifically all pop-up messages that may appear.
     */
    @FXML
    public void loginToAppointments(ActionEvent event) throws IOException {

        String logger = "login_activity.txt";
        FileWriter fWriter = new FileWriter(logger, true);
        PrintWriter outFile = new PrintWriter(fWriter);

        ObservableList<User> userList = DBUsers.getAllUsers();
        if( (userList.get(0).getUserName().equals(userName.getText())
            && userList.get(0).getPassword().equals(passWord.getText())) || (userList.get(1).getUserName().equals(userName.getText()) && userList.get(1).getPassword().equals(passWord.getText())) ) {

            DateTimeFormatter testDTF = DateTimeFormatter.ofLocalizedTime(FormatStyle.LONG).withZone(ZoneId.systemDefault());

            String userNameAttemptSuccess = userName.getText() + " successfully logged in on " + LocalDate.now() + " at " + testDTF.format(LocalTime.now());
            outFile.println(userNameAttemptSuccess);


            int count = 0;
            for(Appointment a : DBAppointments.getAllAppointments()) {
                LocalDateTime startLDT = LocalDateTime.of(a.getStartDate(), a.getStartTimeT());
                LocalDateTime currentLDT = LocalDateTime.now();
                long LDTDifference = ChronoUnit.MINUTES.between(currentLDT, startLDT);

                if(LDTDifference > 0 && LDTDifference <= 15) {
                    Alert appointmentUpcoming = new Alert(Alert.AlertType.INFORMATION);

                    if(Locale.getDefault().getLanguage().equals("fr")) {
                        appointmentUpcoming.setTitle(rb.getString("Appointment_Coming_Up"));
                        appointmentUpcoming.setContentText(rb.getString("appointment_in") + LDTDifference + " " + rb.getString("minsAppId") + a.getAppointmentId() + rb.getString("date") + a.getStartDate() + rb.getString("time") + testDTF.format(a.getStartTimeT()));
                        appointmentUpcoming.showAndWait();
                    }
                    else {
                        appointmentUpcoming.setTitle("Appointment Coming up");
                        appointmentUpcoming.setContentText("You have an appointment in " + LDTDifference + " minute(s)\nAppointment ID: " + a.getAppointmentId() + "\nDate: " + a.getStartDate() + "\nTime: " + testDTF.format(a.getStartTimeT()));
                        appointmentUpcoming.showAndWait();
                    }
                    count++;

                }

            }

            if( !(count > 0) ) {
                Alert noAppToday = new Alert(Alert.AlertType.INFORMATION);

                if(Locale.getDefault().getLanguage().equals("fr")) {
                    noAppToday.setTitle(rb.getString("noAppTitle"));
                    noAppToday.setHeaderText(rb.getString("noAppHT"));
                    noAppToday.setContentText(rb.getString("noAppContext"));
                    noAppToday.showAndWait();
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
        }
        else {
            Alert wrongLoginCreds = new Alert(Alert.AlertType.ERROR);
            if(Locale.getDefault().getLanguage().equals("fr")) {
                wrongLoginCreds.setTitle(rb.getString("wrongLogin1"));
                wrongLoginCreds.setHeaderText(rb.getString("wrongLogin2"));
                wrongLoginCreds.setContentText(rb.getString("wrongLogin3"));
                wrongLoginCreds.showAndWait();
            }
            else {
                wrongLoginCreds.setTitle("Error Login");
                wrongLoginCreds.setHeaderText("Wrong username/password");
                wrongLoginCreds.setContentText("Username or password entered incorrectly");
                wrongLoginCreds.showAndWait();
            }
            DateTimeFormatter testDTF = DateTimeFormatter.ofLocalizedTime(FormatStyle.LONG).withZone(ZoneId.systemDefault());

            String userNameAttemptFail = userName.getText() + " failed to login on " + LocalDate.now() + " at " + testDTF.format(LocalTime.now());
            outFile.println(userNameAttemptFail);
        }

        outFile.close();

    }


    /**
     * Sets and displays the system locale as well as setting labels to french if the system language is set to french.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId localZoneID = ZoneId.of(TimeZone.getDefault().getID());
        String zoneString = localZoneID.toString();
        localZone.setText(zoneString);

        if(Locale.getDefault().getLanguage().equals("fr")) {
            usernameLabel.setText(rb.getString("Username"));
            passwordLabel.setText(rb.getString("Password"));
            loginButtonLabel.setText(rb.getString("Login"));
            titleLabel.setText(rb.getString("User_Login"));
        }
    }

}
