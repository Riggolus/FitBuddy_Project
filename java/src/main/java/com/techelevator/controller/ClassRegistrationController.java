package com.techelevator.controller;

import com.techelevator.dao.ClassRegistrationDao;
import com.techelevator.model.ClassDto;
import com.techelevator.model.ClassRegistration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/class/register")
@PreAuthorize("isAuthenticated()")
public class ClassRegistrationController {


    private final ClassRegistrationDao classRegistrationDao;

    public ClassRegistrationController (ClassRegistrationDao classRegistrationDao){
        this.classRegistrationDao = classRegistrationDao;
    }

    @GetMapping("/{classDto}")
    public ClassRegistration displayRegistration (@PathVariable ("classDto")ClassDto classDto){
        return classRegistrationDao.displayRegistration(classDto);
    }

    @PostMapping()
    public void registerForClass(@RequestBody ClassRegistration classRegistration, Principal principal){
        classRegistrationDao.registerForClass(classRegistration, principal);
    }

}
