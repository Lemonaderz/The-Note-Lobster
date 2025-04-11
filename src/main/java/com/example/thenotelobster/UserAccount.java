package com.example.thenotelobster;

public final class UserAccount {

    private UserAccount currentUser;
    private final static UserAccount INSTANCE = new UserAccount();
    private String email;
    private String userName;
    private String password;

    private UserAccount() {}


    public static UserAccount getInstance() {
        return INSTANCE;
    }

    public void setUser(UserAccount currentUser) {
        this.currentUser = currentUser;
    }

    public UserAccount getUser() {
        return this.currentUser;
    }



    public void  setUser(String email, String userName, String password) {
        // Since the id is auto-incremented, it is nice to have a constructor without it
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
