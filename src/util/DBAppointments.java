package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBAppointments {
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> alist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                String start = rs.getString("Start");
                String end = rs.getString("End");
                Appointment a = new Appointment(appointmentId, title, description, location, type, start, end);
                alist.add(a);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return alist;

    }

    public static void deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE from appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, appointmentId);
        ps.executeUpdate();
    }

}
