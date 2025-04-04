package com.example.thenotelobster;

public class UserAccount {
    private String email;
    private String userName;
    private String password;

    public UserAccount(String email, String userName, String password) {
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

    public void setLastName(String Password) {
        this.password = password;
    }
}
