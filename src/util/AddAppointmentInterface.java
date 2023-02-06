package util;

/**
 * The lambda expression uses this as its method.
 */
public interface AddAppointmentInterface {

    /**
     * Used to compare a contact which is then used to set the contact ID in the database.
     * @param s the contact name to compare
     * @return true or false if the contact names match
     */
    boolean checkContact(String s);
}
