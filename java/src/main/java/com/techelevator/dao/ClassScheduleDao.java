package com.techelevator.dao;

import com.techelevator.model.ClassSchedule;

import java.security.Principal;
import java.util.List;

public interface ClassScheduleDao {

    List<ClassSchedule> getAllClasses();
    boolean createClass(ClassSchedule classSchedule, Principal principal);
    boolean deleteClass(String name);

}
