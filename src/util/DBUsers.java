package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Responsible for getting all users.
 */
public class DBUsers {

    /**
     * Gets all users from the database.
     * @return the ObservableList of all users
     */
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> ulist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from users";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                User u = new User(userId, userName, password);
                ulist.add(u);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ulist;
    }
}
