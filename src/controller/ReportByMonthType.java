package controller;

import com.mysql.cj.result.StringValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;
import model.Appointment;
import util.DBAppointments;

import javax.naming.spi.InitialContextFactory;
import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ReportByMonthType implements Initializable {

    @FXML
    private Label numberOfAppointmentsLabel;

    @FXML
    private ComboBox<Month> monthCB;

    @FXML
    private TableColumn<Appointment, String> monthColRep;

    @FXML
    private TableColumn<Appointment, Integer> numOfAppsCol;

    @FXML
    private TableView<Appointment> report1Table;

    @FXML
    private TextField searchTypeTF;

    @FXML
    private TableColumn<Appointment, String> typeColRep;

    private int counter = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Month> monthList = FXCollections.observableArrayList();
        monthList.addAll(Arrays.asList(Month.values()));
        monthCB.setItems(monthList);
    }

    @FXML
    public void getTypeSearched(ActionEvent event) {

    }

    @FXML
    public void returnToAppointments(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/AppointmentsMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1132, 558);
        //stage.setTitle("Customer/Appointment Form");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void getMonthSelect(ActionEvent event) {

    }

    @FXML
    public void lookUpAppointments(ActionEvent event) {
        counter = 0;
        for(Appointment a : DBAppointments.getAllAppointments()) {
            if( a.getStartDate().getMonth().equals(monthCB.getValue()) && a.getType().equals(searchTypeTF.getText()) ) {
                counter++;
            }
        }
        numberOfAppointmentsLabel.setText(String.valueOf(counter));
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
