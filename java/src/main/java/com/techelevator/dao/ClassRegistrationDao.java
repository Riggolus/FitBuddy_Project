package com.techelevator.dao;

import com.techelevator.model.ClassDto;
import com.techelevator.model.ClassRegistration;

import java.security.Principal;
import java.util.Date;
import java.util.List;

public interface ClassRegistrationDao {

    ClassRegistration displayRegistration(Date date, String className);
    void registerForClass(ClassRegistration classRegistration, Principal principal);
}
