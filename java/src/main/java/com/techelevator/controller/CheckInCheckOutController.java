package com.techelevator.controller;


import com.techelevator.dao.CheckInCheckOutDao;
import com.techelevator.model.CheckInCheckOut;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

    @PostMapping("/in/member")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public void employeeCheckIn(@RequestBody User user, Principal principal){
        checkInCheckOutDao.employeeCheckIn(user.getId(), principal);
    }

    @PutMapping("/out/member")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public void employeeCheckOut(@RequestBody User user, Principal principal){
        checkInCheckOutDao.employeeCheckOut(user.getId(), principal);
    }


}
