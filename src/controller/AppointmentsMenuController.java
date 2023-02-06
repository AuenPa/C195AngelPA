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
import util.DBAppointments;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is responsible for displaying all appointments. All functions that encompass working with appointments are available.
 */
public class AppointmentsMenuController implements Initializable {

    /**
     * TableColumn for the start times.
     */
    @FXML
    private TableColumn<Appointment, LocalTime> startTimeCol;

    /**
     * TableColumn for the end times.
     */
    @FXML
    private TableColumn<Appointment, LocalTime> endTimeCol;

    /**
     * TableColumn for the appointment ID.
     */
    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;

    /**
     * TableColumn for the contact names.
     */
    @FXML
    private TableColumn<Appointment, Integer> contactNameCol;

    /**
     * TableColumn for the descriptions.
     */
    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    /**
     * TableView for the appointments.
     */
    @FXML
    private TableView<Appointment> appointmentTable;

    /**
     * TableColumn for the locations.
     */
    @FXML
    private TableColumn<Appointment, String> locationCol;

    /**
     * TableColumn for the start date.
     */
    @FXML
    private TableColumn<Appointment, LocalDate> startDateCol;

    /**
     * TableColumn for the titles.
     */
    @FXML
    private TableColumn<Appointment, String> titleCol;

    /**
     * TableColumn for the type.
     */
    @FXML
    private TableColumn<Appointment, String> typeCol;

    /**
     * TableColumn for the user ID.
     */
    @FXML
    private TableColumn<Appointment, Integer> userIdCol;

    /**
     * TableColumn for the customer ID.
     */
    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;

    /**
     * The appointment instance that is selected to be deleted.
     */
    private Appointment SA;

    /**
     * Deletes the appointment from the table as well as from the database.
     * The selected appointment is deleted but not before an alert is shown asking if this is what the user wants done.
     * If the appointment is chosen for deletion, another alert is shown right after informing the details of the appointment that was deleted.
     * If the delete button is clicked when no appointment is selected, there is an error alert stating that nothing was selected to delete.
     */
    @FXML
    public void deleteAppointment(ActionEvent event) {
        try {
            SA = appointmentTable.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected appointment, are you sure?");

            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {
                DBAppointments.deleteAppointment(SA.getAppointmentId());
                appointmentTable.setItems(DBAppointments.getAllAppointments());
                Alert deleteComplete = new Alert(Alert.AlertType.INFORMATION);
                deleteComplete.setTitle("Appointment Cancelled");
                deleteComplete.setContentText("Appointment number " + SA.getAppointmentId() + " of type " + SA.getType() + " has been cancelled");
                deleteComplete.setHeaderText("Cancellation");
                deleteComplete.showAndWait();
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
    }

    /**
     * Sets all of the appointments on the table.
     * All columns are set to populate with their respective data.
     */
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
     * Switches to the update appointment screen.
     * When this action is taken, the passAppointment static method is called and it passes the selected appointment.
     * If nothing is selected, a message is displayed saying nothing was selected and nothing happens.
     */
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

    /**
     * Switches to the add appointment screen.
     */
    @FXML
    public void toAddAppointment(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 695, 430);
        //stage.setTitle("From appointment menu to add appointment");
        stage.setScene(scene);
        stage.show();
    }

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
     * Switches to the report of appointments by contacts screen.
     */
    @FXML
    public void toggleToReports2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportAppByContacts.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1132, 558);
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


}
