package com.techelevator.controller;


import com.techelevator.dao.CheckInCheckOutDao;
import com.techelevator.model.CheckInCheckOut;
import com.techelevator.model.User;
import com.techelevator.model.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/check")
@PreAuthorize("isAuthenticated()")
public class CheckInCheckOutController {

    @Autowired
    private CheckInCheckOutDao checkInCheckOutDao;

    @PostMapping("/in")
    public void checkIn(Principal principal) {
        checkInCheckOutDao.checkIn(principal);
    }

    @PutMapping("/out")
    public void checkOut(Principal principal) {
        checkInCheckOutDao.checkOut(principal);
    }

    @GetMapping("/mystatus")
    public CheckInCheckOut myStatus(Principal principal){
        CheckInCheckOut checkInCheckOut = checkInCheckOutDao.myStatus(principal);
        return checkInCheckOut;
    }

    @PostMapping("/in/member")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public void employeeCheckIn(@RequestBody UserStatus user, Principal principal){
        checkInCheckOutDao.employeeCheckIn(user.getUserId(), principal);
    }

    @PutMapping("/out/member")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public void employeeCheckOut(@RequestBody UserStatus user, Principal principal){
        checkInCheckOutDao.employeeCheckOut(user.getUserId(), principal);
    }

    @GetMapping("/status")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public CheckInCheckOut checkStatus(@RequestBody User user) {
        CheckInCheckOut checkInCheckOut = checkInCheckOutDao.getCheckInTime(user.getId());
        return checkInCheckOut;
    }
    @GetMapping("/statuses")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public List<UserStatus> usersStatuses(){
      List<UserStatus> users =  checkInCheckOutDao.getUserStatuses();
      return users;
    }

}
