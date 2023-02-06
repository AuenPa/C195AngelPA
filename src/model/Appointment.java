package model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents an appointment.
 */
public class Appointment {

    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDate startDate;
    private LocalTime startTimeT;
    private LocalDate endDate;
    private LocalTime endTimeT;
    private int assocCustomerId;
    private String contactName;
    private int userId;

    public Appointment(int appointmentId, String title, String description, String location, String type, LocalTime startTimeT, LocalTime endTimeT, int assocCustomerId, String contactName, int userId, LocalDate startDate, LocalDate endDate) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTimeT = startTimeT;
        this.endTimeT = endTimeT;
        this.assocCustomerId = assocCustomerId;
        this.contactName = contactName;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     *
     * @return the customer ID
     */
    public int getAssocCustomerId() {
        return assocCustomerId;
    }

    /**
     *
     * @param assocCustomerId the customer ID to set
     */
    public void setAssocCustomerId(int assocCustomerId) {
        this.assocCustomerId = assocCustomerId;
    }

    /**
     *
     * @return the appointment ID
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     *
     * @param appointmentId the appointment ID to set
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     *
     * @return the appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title the appointment title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return the appointment description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description the appointment description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return the appointment location
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location the appointment location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return the appointment type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type the appointment type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return the appointment start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     *
     * @param startDate the appointment start date to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     *
     * @return the appointment end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     *
     * @param endDate the appointment end date to set
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     *
     * @return the appointment contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     *
     * @param contactName the appointment contact name to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     *
     * @return the appointment user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     *
     * @param userId the appointment user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     *
     * @return the appointment start time
     */
    public LocalTime getStartTimeT() {
        return startTimeT;
    }

    /**
     *
     * @param startTimeT the appointment start time to set
     */
    public void setStartTimeT(LocalTime startTimeT) {
        this.startTimeT = startTimeT;
    }

    /**
     *
     * @return the appointment end time
     */
    public LocalTime getEndTimeT() {
        return endTimeT;
    }

    /**
     *
     * @param endTimeT the appointment end time to set
     */
    public void setEndTimeT(LocalTime endTimeT) {
        this.endTimeT = endTimeT;
    }
}
