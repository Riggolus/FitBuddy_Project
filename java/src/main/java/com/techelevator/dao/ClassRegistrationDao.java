package com.techelevator.dao;

import com.techelevator.model.ClassDto;
import com.techelevator.model.ClassRegistration;

import java.security.Principal;
import java.util.List;

public interface ClassRegistrationDao {

    ClassRegistration displayRegistration(ClassDto classDto);
    void registerForClass(ClassRegistration classRegistration, Principal principal);
}
