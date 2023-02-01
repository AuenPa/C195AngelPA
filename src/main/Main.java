package main;

import util.DBCustomers;
import util.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/User.fxml"));
        //primaryStage.setTitle("Login Menu");
        primaryStage.setScene(new Scene(root, 743, 526));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {

        //ResourceBundle rb = ResourceBundle.getBundle("util/Lang", Locale.getDefault());
/*
        if(Locale.getDefault().getLanguage().equals("fr")) {
            System.out.println(rb.getString("hello") + " " + rb.getString("world"));
        }
 */

        JDBC.openConnection();

        launch(args);

        JDBC.closeConnection();
    }
}
