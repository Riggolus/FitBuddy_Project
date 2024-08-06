package com.techelevator.model;

import java.util.Date;

public class CheckInCheckOut {

    private int checkInCheckOutId;
    private int userId;
    private Date checkInTime;
    private Date checkOutTime;
    private Integer checkInBy;

    public Integer getCheckOutBy() {
        return checkOutBy;
    }

    public void setCheckOutBy(Integer checkOutBy) {
        this.checkOutBy = checkOutBy;
    }

    private Integer checkOutBy;


    public CheckInCheckOut(int checkInCheckOutId, int userId, Date checkInTime, Date checkOutTime, Integer checkInBy, Integer checkOutBy) {
        this.checkInCheckOutId = checkInCheckOutId;
        this.userId = userId;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.checkInBy = checkInBy;
        this.checkOutBy = checkOutBy;
    }

    public CheckInCheckOut(){

    }

    public int getCheckInCheckOutId() {
        return checkInCheckOutId;
    }

    public void setCheckInCheckOutId(int checkInCheckOutId) {
        this.checkInCheckOutId = checkInCheckOutId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public Integer getCheckInBy() {
        return checkInBy;
    }

    public void setCheckInBy(Integer checkInBy) {
        this.checkInBy = checkInBy;
    }
}
