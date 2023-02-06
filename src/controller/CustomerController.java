package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import util.DBAppointments;
import util.DBCustomers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is responsible for displaying all customers in a table and providing all functions to work on those customers.
 */
public class CustomerController implements Initializable {


    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> tableColumn1;

    @FXML
    private TableColumn<Customer, String> tableColumn2;

    @FXML
    private TableColumn<Customer, String> tableColumn3;

    @FXML
    private TableColumn<Customer, String> tableColumn4;

    @FXML
    private TableColumn<Customer, String> tableColumn5;

    @FXML
    private TableColumn<Customer, Integer> tableColumn6;

    @FXML
    private TableColumn<Customer, String> divisionName;

    private Customer SC;

    /**
     * Deletes the selected customer.
     * If the customer has any appointments, this process will not succeed.
     * Only when all appointments associated with this customer are deleted will the customer finally be able to be deleted.
     * An alert stating there was an error will display if there are appointments associated with the customer.
     * If no customer is selected an error will pop-up stating that nothing was selected and nothing will be done.
     */
    @FXML
    public void deleteSelectedCustomer(ActionEvent event) {

        try {
            SC = customerTable.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected customer, are you sure?");

            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {
                DBCustomers.deleteCustomer(SC.getCustomerId());
                //Customer.deleteCustomerCM(SC);
                customerTable.setItems(DBCustomers.getAllCustomers());
                Alert deleteComplete = new Alert(Alert.AlertType.INFORMATION);
                deleteComplete.setTitle("Deletion Complete");
                deleteComplete.setHeaderText("Customer Removed");
                deleteComplete.show();
            }

        }

        catch (SQLException | NullPointerException e) {
            for(Appointment a : DBAppointments.getAllAppointments()) {
                //had to add SC != null before because it doesn't go to the next and final if statement (i.e., nothing selected)
                if(SC != null && SC.getCustomerId() == a.getAssocCustomerId()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Delete associated appointment(s) first");
                    alert.showAndWait();
                    break;
                }
            }
            if(SC == null) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR, "Nothing selected");
                alert1.setTitle("Error");
                alert1.setContentText("Nothing selected");
                alert1.showAndWait();
            }
            e.printStackTrace();
        }
    }

    /**
     * Switches to the add customer screen.
     */
    @FXML
    public void toAddCustomerScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 720, 540);
        //stage.setTitle("From customer menu to add customer");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Switches to the update customer screen.
     * When this action is taken, the passCustomer static method is called and it passes the selected customer.
     * If nothing is selected, a message is displayed saying nothing was selected and nothing happens.
     */
    @FXML
    public void toUpdateCustomerScreen(ActionEvent event) throws IOException {
        Customer SC = customerTable.getSelectionModel().getSelectedItem();
        UpdateCustomerController.passCustomer(SC);

        if(SC == null) {
            System.out.println("Customer not selected to modify");
            return;
        }

        Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateCustomer.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 720, 540);
        //stage.setTitle("From customer menu to update customer");
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
     * Sets the customer table with all of the customers in the database.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTable.setItems(DBCustomers.getAllCustomers());

        tableColumn1.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tableColumn2.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tableColumn3.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableColumn4.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        tableColumn5.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tableColumn6.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        divisionName.setCellValueFactory(new PropertyValueFactory<>("division"));

    }

    /**
     * Switches to the all appointments screen.
     */
    @FXML
    public void toggleToAllApps(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1132, 558);
        //stage.setTitle("From customer to appointments");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches to appointments by month screen.
     */
    @FXML
    public void toggleToAppMonth(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsByMonth.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1132, 558);
        //stage.setTitle("From customer to appointments by month");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches to appointments by week screen.
     */
    @FXML
    public void toggleToAppWeek(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentByWeek.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1132, 558);
        //stage.setTitle("From appointments to apps by week");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches to the reports screen.
     * The reports on number of appointments by month and type is shown by default.
     */
    @FXML
    public void toggleToReports(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportByMonth_Type.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        //stage.setTitle("From appointment menu to update appointment");
        stage.setScene(scene);
        stage.show();
    }
}