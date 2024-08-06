package com.techelevator.dao;

import com.techelevator.model.CheckInCheckOut;

import java.security.Principal;

public interface CheckInCheckOutDao {

    void checkIn(Principal principal);
    void checkOut(Principal principal);
    void employeeCheckIn(int userId, Principal principal);
    void employeeCheckOut(int userId, Principal principal);
    CheckInCheckOut getCheckInTime(int userId);
}
