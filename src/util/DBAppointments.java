package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Responsible for adding, deleting, updating, and retrieving the appointment data from the database.
 */
public class DBAppointments {

    /**
     * Retrieves all appointments from the database and creates a new instance of appointment
     * @return the ObservableList of all appointments from the database
     */
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

    /**
     * Adds the appointment to the database.
     * @param title the title to set
     * @param description the description to set
     * @param location the location to set
     * @param type the type to set
     * @param startT the start time to set
     * @param endT the end time to set
     * @param assocCustId the associated customer ID to set
     * @param contactId the contact ID to set
     * @param userId the user ID to set
     */
    public static void addAppointment(String title, String description, String location, String type, Timestamp startT, Timestamp endT, int assocCustId, int contactId, int userId) {
        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, Contact_ID, User_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, startT);
            ps.setTimestamp(6, endT);
            ps.setInt(7, assocCustId);
            ps.setInt(8, contactId);
            ps.setInt(9, userId);
            ps.execute();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the appointment in the database.
     * @param appointmentId the appointment ID to set
     * @param title the title to set
     * @param description the description to set
     * @param location the location to set
     * @param type the type to set
     * @param startT the start time to set
     * @param endT the end time to set
     * @param assocCustId the associated customer id to set
     * @param contactId the contact ID to set
     * @param userId the user ID to set
     */
    public static void updateAppointment(int appointmentId, String title, String description, String location, String type, Timestamp startT, Timestamp endT, int assocCustId, int contactId, int userId) {
        try {
            String sql = "UPDATE appointments set Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, Contact_ID = ?, User_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement pst = JDBC.getConnection().prepareStatement(sql);
            pst.setString(1, title);
            pst.setString(2, description);
            pst.setString(3, location);
            pst.setString(4, type);
            pst.setTimestamp(5, startT);
            pst.setTimestamp(6, endT);
            pst.setInt(7, assocCustId);
            pst.setInt(8, contactId);
            pst.setInt(9, userId);
            pst.setInt(10, appointmentId);
            pst.execute();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the appointment from the database.
     * @param appointmentId the appointment ID to set
     * @throws SQLException
     */
    public static void deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE from appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, appointmentId);
        ps.executeUpdate();
    }

}
