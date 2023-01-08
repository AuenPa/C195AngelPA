package controller;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
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
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;
import java.util.ResourceBundle;

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
            }

        }

        catch (SQLException | NullPointerException e) {
            for(Appointment a : DBAppointments.getAllAppointments()) {
                //had to add SC != null before because it doesn't go to the next and final if statement (i.e., nothing selected)
                if(SC != null && SC.getCustomerId() == a.getAssocCustomerId()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Delete associated appointment first");
                    alert.setTitle("Error");
                    alert.setContentText("Can't delete");
                    alert.showAndWait();
                }
            }
            if(SC == null) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR, "Nothing selected");
                alert1.setTitle("Error");
                alert1.setContentText("Nothing selected");
                alert1.showAndWait();
            }
            e.printStackTrace();
            //System.out.println(e.getMessage());
        }
    }

    @FXML
    public void toAddCustomerScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 720, 540);stage.setTitle("From customer menu to add customer");
        stage.setScene(scene);
        stage.show();

    }

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
        Scene scene = new Scene(root, 720, 540);stage.setTitle("From customer menu to update customer");
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
            stage.setTitle("Logged out");
            stage.setScene(scene);
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //customerTable.setItems(Customer.getAllCustomersCM());
        customerTable.setItems(DBCustomers.getAllCustomers());

        tableColumn1.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tableColumn2.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tableColumn3.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableColumn4.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        tableColumn5.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tableColumn6.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        divisionName.setCellValueFactory(new PropertyValueFactory<>("division"));

    }

    @FXML
    public void toggleToAllApps(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1259, 558);
        stage.setTitle("From customer to appointments");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void toggleToAppMonth(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsByMonth.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1259, 558);
        stage.setTitle("From customer to appointments by month");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void toggleToAppWeek(ActionEvent event) {
    }
}