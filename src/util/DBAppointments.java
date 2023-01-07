package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DBAppointments {
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> alist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Contact_Name, Type, Start, End, Customer_ID, appointments.User_ID FROM appointments, contacts, users WHERE appointments.Contact_ID = contacts.Contact_ID AND appointments.User_ID = users.User_ID";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");

                //String start = rs.getString("Start");
                Timestamp startT = rs.getTimestamp("Start");
                LocalDateTime startDateTime = startT.toLocalDateTime();
                LocalDate startLDate = startDateTime.toLocalDate();
                LocalTime startLTime = startDateTime.toLocalTime();

                //String end = rs.getString("End");
                Timestamp endT = rs.getTimestamp("End");
                LocalDateTime endDateTime = endT.toLocalDateTime();
                LocalDate endLDate = endDateTime.toLocalDate();
                LocalTime endLTime = endDateTime.toLocalTime();

                int assocCustomerId = rs.getInt("Customer_ID");
                String contactName = rs.getString("Contact_Name");
                int userId = rs.getInt("User_ID");

                Appointment a = new Appointment(appointmentId, title, description, location, type, startLTime, endLTime, assocCustomerId, contactName, userId, startLDate, endLDate);
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
