package com.example.thenotelobster;

public class UserAccount {
    private int id;
    private String userName;
    private String password;

    public UserAccount(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public UserAccount(String userName, String password) {
        // Since the id is auto-incremented, it is nice to have a constructor without it
        this.userName = userName;
        this.password = password;
    }

    public int getId() {
        return id;
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
