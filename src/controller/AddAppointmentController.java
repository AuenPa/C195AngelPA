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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import util.DBAppointments;
import util.DBContacts;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {


    @FXML
    private TextField appointmentID;

    @FXML
    private ComboBox<String> contactNameComboBox;

    @FXML
    private TextField customerIdTF;

    @FXML
    private TextField descriptionTF;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField endDateTF;

    @FXML
    private ComboBox<LocalTime> endTimeCB;

    @FXML
    private TextField locationTF;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private TextField startDateTF;

    @FXML
    private ComboBox<LocalTime> startTimeCB;

    @FXML
    private TextField titleTF;

    @FXML
    private TextField typeTF;

    @FXML
    private TextField userIdTF;

    private int getContactId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        /*
        //this won't fully populate the list it will depend on whether or not the appointments have all three contacts listed
        for(Appointment a : DBAppointments.getAllAppointments()){
            contactNames.add(a.getContactName());
        }
        contactNameComboBox.setItems(contactNames);
         */
        for(Contact c : DBContacts.getAllContacts()){
            contactNames.add(c.getContactName());
        }
        contactNameComboBox.setItems(contactNames);

    }

    @FXML
    public void cancelAddAppointment(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1259, 558);
        stage.setTitle("From customer to appointments");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void saveAddAppointment(ActionEvent event) {
        //Appointment newAPP = new Appointment(2, "hello", "thisDesc", "here", "test", startTimeCB.getValue(), endTimeCB.getValue(), 1, contactNameComboBox.getValue(), 3, startDatePicker.getValue(), endDatePicker.getValue());
        //int getContactId = 0;
        String addTitle = titleTF.getText();
        String addDescription = descriptionTF.getText();
        String addLocation = locationTF.getText();
        String addType = typeTF.getText();
        LocalDateTime addStartCombine = LocalDateTime.of(startDatePicker.getValue(), startTimeCB.getValue());
        Timestamp addLDTConvertStart = Timestamp.valueOf(addStartCombine);
        LocalDateTime addEndCombine = LocalDateTime.of(endDatePicker.getValue(), endTimeCB.getValue());
        Timestamp addLDTConvertEnd = Timestamp.valueOf(addEndCombine);
        int addAssocCustomerId = Integer.parseInt(customerIdTF.getText());
        int addUserId = Integer.parseInt(userIdTF.getText());
        for(Contact c : DBContacts.getAllContacts()){
            if(c.getContactName().equals(contactNameComboBox.getValue())){
                getContactId = c.getContactId();
                break;
            }
        }
        DBAppointments.addAppointment(addTitle, addDescription, addLocation, addType, addLDTConvertStart, addLDTConvertEnd, addAssocCustomerId, getContactId, addUserId);

    }
}
