package model;

/**
 * Represents a user.
 */
public class User {
    /**
     * The user ID variable of a User object
     */
    private int userId;
    /**
     * The username variable of a User object
     */
    private String userName;
    /**
     * The password variable of a User object
     */
    private String password;

    /**
     * Constructor of a User object.
     * @param userId user ID to set
     * @param userName username to set
     * @param password password to set
     */
    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**
     *
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     *
     * @param userId the user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     *
     * @return the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName the username to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
