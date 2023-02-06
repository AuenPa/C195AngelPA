package main;

import util.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Locale;

/**
 * Responsible for setting the initial stage and creating a connection to the database.
 * Also closes the connection when closing the program.
 */
public class Main extends Application {

    /**
     * Sets initial stage.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/User.fxml"));
        //primaryStage.setTitle("Login Menu");
        primaryStage.setScene(new Scene(root, 743, 526));
        primaryStage.show();
    }


    /**
     * Makes the connection and closes it when program is closed.
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {

        //Locale.setDefault(new Locale("fr"));
        JDBC.openConnection();

        launch(args);

        JDBC.closeConnection();
    }
}
