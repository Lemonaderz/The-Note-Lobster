package com.example.thenotelobster.model.UserClasses;

/**
 * Singleton class representing the currently logged-in user.
 *
 * <p>This class ensures that only one instance of a user is active in memory at a time,
 * using the Singleton design pattern.</p>
 */
public final class UserAccount {

/** Holds reference to the current user */
    private UserAccount currentUser;

    /** Static instance for Singleton pattern. */
    private final static UserAccount INSTANCE = new UserAccount();

    /** Account email */
    private String email;
    /** Account username. */
    private String userName;
    /** Account password. */
    private String password;

    /**
     * Private constructor to prevent external instantiation.
     * Singleton pattern enforced here.
     */
    private UserAccount() {}

    /**
     * Get the Singleton instance of the current user.
     *
     * @return the single instance of UserAccount.
     */
    public static UserAccount getInstance() {
        return INSTANCE;
    }

    /**
     * Sets the current user using another UserAccount object.
     *
     * @param currentUser the UserAccount to copy into the Singleton.
     */
    public void setUser(UserAccount currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Sets user information for the current Singleton instance.
     *
     * @param email     the user's email address.
     * @param userName  the username.
     * @param password  the user's password.
     */
    public void  setUser(String email, String userName, String password) {
        // Since the id is auto-incremented, it is nice to have a constructor without it
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Gets the user's email address.
     *
     * @return the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the user's username.
     *
     * @return the username.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the user's password.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's email address.
     *
     * @param email the new email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the user's username.
     *
     * @param userName the new username to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Sets the user's password.
     *
     * @param password the new password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }


}
