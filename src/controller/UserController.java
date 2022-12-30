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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import util.DBUsers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private TextField passWord;

    @FXML
    private TextField userName;

    @FXML
    public void loginToAppointments(ActionEvent event) throws IOException {

        ObservableList<User> userList = DBUsers.getAllUsers();
        int counter = 0;
        for(User u : userList) {
            if(u.getUserName().equals(userName.getText())
            && u.getPassword().equals(passWord.getText()) ) {
                System.out.println("Successful login!");

                Parent root = FXMLLoader.load(getClass().getResource("../view/AppointmentsMenu.fxml"));
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 871, 558);
                stage.setTitle("Customer/Appointment Form");
                stage.setScene(scene);
                stage.show();
                counter++;
            }
        }
        if(counter == 2) {
            System.out.println("Wrong username or password");
            //counter = 0;
        }
        counter = 0;

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
