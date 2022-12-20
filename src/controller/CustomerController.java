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
import model.Customer;
import util.DBCustomers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    public void deleteSelectedCustomer(ActionEvent event) {
        try {
            Customer SC = customerTable.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected customer, are you sure?");

            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {
                DBCustomers.deleteCustomer(SC.getCustomerId());
            }
        }
        catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void toAddCustomerScreen(ActionEvent event) {

    }

    @FXML
    public void toUpdateCustomerScreen(ActionEvent event) {

    }

    @FXML
    public void goToAppointments(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 871, 558);
        stage.setTitle("From customer to appointments");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTable.setItems(DBCustomers.getAllCustomers());

        tableColumn1.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tableColumn2.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tableColumn3.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableColumn4.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        tableColumn5.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tableColumn6.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

    }
}