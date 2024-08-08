package com.techelevator.dao;

import com.techelevator.model.CheckInCheckOut;
import com.techelevator.model.UserStatus;

import java.security.Principal;
import java.util.List;

public interface CheckInCheckOutDao {

    void checkIn(Principal principal);
    void checkOut(Principal principal);
    void employeeCheckIn(int userId, Principal principal);
    void employeeCheckOut(int userId, Principal principal);
    CheckInCheckOut getCheckInTime(int userId);
    List<UserStatus> getUserStatuses();
    CheckInCheckOut myStatus(Principal principal);
}
