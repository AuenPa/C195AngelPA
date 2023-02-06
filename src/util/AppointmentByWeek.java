package util;

import java.time.Month;

/**
 * The lambda expression in the 'appointment by week' controller uses this interface.
 */
public interface AppointmentByWeek {
    /**
     * Used to compare the months to get a filtered list of appointments
     * @param m the month to set
     * @return true or false if the selected month is equal to an appointments month
     */
    boolean compareMonth(Month m);
}
