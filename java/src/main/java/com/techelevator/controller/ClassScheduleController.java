package com.techelevator.controller;

import com.techelevator.dao.ClassScheduleDao;
import com.techelevator.model.ClassSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/class-schedule")
@PreAuthorize("isAuthenticated()")
public class ClassScheduleController {

    private final ClassScheduleDao classScheduleDao;

    @Autowired
    public ClassScheduleController(ClassScheduleDao classScheduleDao) {
        this.classScheduleDao = classScheduleDao;
    }

    @GetMapping("/all")
    public List<ClassSchedule> getAllClasses() {
        return classScheduleDao.getAllClasses();
    }
}
