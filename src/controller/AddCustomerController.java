package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import util.DBCustomers;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    @FXML
    private TextField address;

    @FXML
    private TextField customerID;

    @FXML
    private TextField customerName;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField postalCode;

    @FXML
    private TextField tempDivisionID;

    @FXML
    public void cancelAddCustomer(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerApplicationMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 890, 540);
        stage.setTitle("From add customer to customer");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void saveAddCustomer(ActionEvent event) {
        DBCustomers.addCustomer(customerName.getText(), address.getText(), postalCode.getText(), phoneNumber.getText(), Integer.parseInt(tempDivisionID.getText()));
        //Customer.addCustomerCM();
/*
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerApplicationMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 890, 540);
        stage.setTitle("From add customer to customer");
        stage.setScene(scene);
        stage.show();

 */
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
