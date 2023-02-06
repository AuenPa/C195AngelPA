package model;

/**
 * Represents a contact.
 */
public class Contact {

    /**
     * The contact ID variable of a Contact object.
     */
    private int contactId;
    /**
     * The contact name variable of a Contact object.
     */
    private String contactName;
    /**
     * The contact email variable of a Contact object.
     */
    private String email;

    /**
     * Constructor for a Contact object.
     * @param contactId the contact ID to set
     * @param contactName the contact name to set
     * @param email the contact email to set
     */
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     *
     * @return the customer contact ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     *
     * @param contactId the customer contact ID to set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     *
     * @return the customer contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     *
     * @param contactName the customer contact name to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     *
     * @return the customer email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email the customer email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
