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
import util.DBAppointments;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentBWController implements Initializable {
    @FXML
    private RadioButton allAppointmentRB;

    @FXML
    private RadioButton appointmentMonthRB;

    @FXML
    private RadioButton appointmentWeekRB;

    @FXML
    private RadioButton customerRB;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, Integer> contactNameCol;

    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;

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
        //CHANGE MONTH TO WEEK ON OBSERVABLELIST OF APPOINTMENTS
        //appointmentTable.setItems(DBAppointments.getAllAppointments());
        ObservableList<Appointment> filterAppsByWeekList = FXCollections.observableArrayList();

        ZoneId z = ZoneId.systemDefault();
        LocalDate today = LocalDate.now(z);
        int wOfWBasedY = today.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        int wOfY = today.get(IsoFields.WEEK_BASED_YEAR);
        String todayWeekISO = wOfY + "-W" + String.format("%02d", wOfWBasedY);

        //Instead of using IsoFields, use WeekFields
        //I tried using them a while back but they did not register
        //I'm not sure why. Anyways...
        //This is used to get the current week of the year in terms of a week starting on a Sunday
        TemporalField currentWeekOfYear = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        LocalDate todaysDate = LocalDate.now();
        int presentWeekOfYear = todaysDate.get(currentWeekOfYear);

        for(Appointment a : DBAppointments.getAllAppointments()) {
            /*
            LocalDate fromDBAppointment = a.getStartDate();
            int fromDBAppWWBY = fromDBAppointment.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
            int fromDBAppWY = fromDBAppointment.get(IsoFields.WEEK_BASED_YEAR);
            String compareFromDBApps = fromDBAppWY + "-W" + String.format("%02d", fromDBAppWWBY);
             */
            //Need to make localdatetime of appointment a to get appointment week number *MAYBE NOT AFTER ALL*
            LocalDateTime appLDT = LocalDateTime.of(a.getStartDate(),a.getStartTimeT());
            TemporalField appointmentWeek = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
            int appWeekNum = a.getStartDate().get(appointmentWeek);
            if(a.getStartDate().getMonth() == LocalDate.now().getMonth() &&
                    a.getStartDate().getYear() == LocalDate.now().getYear() &&
                    appWeekNum == presentWeekOfYear
               ) {
                filterAppsByWeekList.add(a);
                //System.out.println(appWeekNum);
            }
            System.out.println(appWeekNum);
        }


        //System.out.println(presentWeekOfYear);

        appointmentTable.setItems(filterAppsByWeekList);

        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        contactNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTimeT"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTimeT"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("assocCustomerId"));


        //appointmentMonthRB.setSelected(true);
        //allAppointmentRB.setSelected(false);
    }

    @FXML
    public void toggleToAllAppo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1132, 558);
        //stage.setTitle("From appointments by week to all apps");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void toggleToAppoMonth(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsByMonth.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1132, 558);
        //stage.setTitle("From appointments by week to all apps");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void toggleToAppoWeek(ActionEvent event) {

    }

    @FXML
    public void toggleToCustomer(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerApplicationMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 890, 558);
        //stage.setTitle("From appointments by week to customer");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void addAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 695, 430);
        //stage.setTitle("From appointment menu to add appointment");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void updateAppointment(ActionEvent event) throws IOException {
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
}
