package model;

import java.time.LocalDate;
import java.time.LocalTime;

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

    public int getAssocCustomerId() {
        return assocCustomerId;
    }

    public void setAssocCustomerId(int assocCustomerId) {
        this.assocCustomerId = assocCustomerId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalTime getStartTimeT() {
        return startTimeT;
    }

    public void setStartTimeT(LocalTime startTimeT) {
        this.startTimeT = startTimeT;
    }

    public LocalTime getEndTimeT() {
        return endTimeT;
    }

    public void setEndTimeT(LocalTime endTimeT) {
        this.endTimeT = endTimeT;
    }
}
