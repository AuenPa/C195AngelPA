package model;

/**
 * Represents a contact.
 */
public class Contact {

    private int contactId;
    private String contactName;
    private String email;

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
