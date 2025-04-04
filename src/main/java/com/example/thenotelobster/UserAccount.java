package com.example.thenotelobster;

public class UserAccount {
    private String userName;
    private String password;

    public UserAccount(String userName, String password) {
        // Since the id is auto-incremented, it is nice to have a constructor without it
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setLastName(String Password) {
        this.password = password;
    }
}
