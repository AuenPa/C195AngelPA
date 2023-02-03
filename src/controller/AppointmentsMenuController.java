package controller;

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
import model.Customer;
import util.DBAppointments;
import util.DBCustomers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentsMenuController implements Initializable {

    @FXML
    private TableColumn<Appointment, LocalTime> startTimeCol;

    @FXML
    private TableColumn<Appointment, LocalTime> endTimeCol;

    @FXML
    private RadioButton allAppointmentRB;

    @FXML
    private RadioButton appointmentMonthRB;

    @FXML
    private RadioButton appointmentWeekRB;

    @FXML
    private RadioButton customerRB;

    @FXML
    private RadioButton reportsRB1;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;

    @FXML
    private TableColumn<Appointment, Integer> contactNameCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private TableColumn<Appointment, LocalDate> startDateCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, Integer> userIdCol;

    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;

    private Appointment SA;

    @FXML
    public void deleteAppointment(ActionEvent event) {
        try {
            SA = appointmentTable.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected appointment, are you sure?");

            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {
                DBAppointments.deleteAppointment(SA.getAppointmentId());
                appointmentTable.setItems(DBAppointments.getAllAppointments());
            }
        }
        catch (SQLException | NullPointerException e) {
            if(SA == null) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR, "Nothing selected");
                alert1.setTitle("Error");
                alert1.setContentText("Nothing selected");
                alert1.showAndWait();
            }
            e.printStackTrace();
        }
        //appointmentTable.setItems(DBAppointments.getAllAppointments());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentTable.setItems(DBAppointments.getAllAppointments());

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
/*
        for(Appointment a : DBAppointments.getAllAppointments()) {
            LocalTime startTime = a.getStartTimeT();
            LocalTime currentTime = LocalTime.now();
            long timeDifference = ChronoUnit.MINUTES.between(currentTime, startTime);
            LocalDateTime startLDT = LocalDateTime.of(a.getStartDate(), a.getStartTimeT());
            LocalDateTime currentLDT = LocalDateTime.now();
            long LDTDifference = ChronoUnit.MINUTES.between(currentLDT, startLDT);
            LocalDate appDate = a.getStartDate();
            LocalDate todaysDate = LocalDate.now();
            if(LDTDifference > 0 && LDTDifference <= 15) {
                Alert appointmentUpcoming = new Alert(Alert.AlertType.INFORMATION);
                appointmentUpcoming.setTitle("Appointment Coming up");
                appointmentUpcoming.setContentText("You have an appointment in " + timeDifference + " minute(s)\nAppointment ID: " + a.getAppointmentId());
                appointmentUpcoming.showAndWait();
            }
            else if(LDTDifference <= 0 && appDate.equals(todaysDate) ) {
                Alert appPastDue = new Alert(Alert.AlertType.INFORMATION);
                appPastDue.setTitle("Appointment past due");
                appPastDue.setContentText("Appointment started " + timeDifference * -1 + " minutes ago\nAppointment ID: " + a.getAppointmentId());
                appPastDue.showAndWait();
            }
        }

 */
    }

    @FXML
    public void toggleToAllAppo(ActionEvent event) {

    }

    @FXML
    public void toggleToAppoMonth(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsByMonth.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1132, 558);
        //stage.setTitle("From appointments to apps by month");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void toggleToAppoWeek(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentByWeek.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1132, 558);
        //stage.setTitle("From appointments to apps by week");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void toggleToCustomer(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerApplicationMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 890, 558);
        //stage.setTitle("From appointments to customer");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void toUpdateAppointment(ActionEvent event) throws IOException {
        Appointment SA = appointmentTable.getSelectionModel().getSelectedItem();
        UpdateAppointmentController.passAppointment(SA);

        if(SA == null) {
            System.out.println("Appointment not selected to modify");
            return;
        }

        Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 695, 430);
        //stage.setTitle("From appointment menu to update appointment");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void toAddAppointment(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 695, 430);
        //stage.setTitle("From appointment menu to add appointment");
        stage.setScene(scene);
        stage.show();
    }
/*
    @FXML
    public void toggleToReports(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportByMonth_Type.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        //stage.setTitle("From appointment menu to update appointment");
        stage.setScene(scene);
        stage.show();
    }

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
