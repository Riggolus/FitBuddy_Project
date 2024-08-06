package com.techelevator.model;

import java.util.Date;

public class UserStatus {

    private int userId;

    public UserStatus(int userId, String username, Date checkedIn, Date checkedOut) {
        this.userId = userId;
        this.username = username;
        this.checkedIn = checkedIn;
        this.checkedOut = checkedOut;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(Date checkedIn) {
        this.checkedIn = checkedIn;
    }

    public Date getCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(Date checkedOut) {
        this.checkedOut = checkedOut;
    }

    private String username;
    private Date checkedIn;
    private Date checkedOut;

}
