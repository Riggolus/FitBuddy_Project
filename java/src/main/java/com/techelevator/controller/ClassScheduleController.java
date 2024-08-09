package com.techelevator.controller;

import com.techelevator.dao.ClassScheduleDao;
import com.techelevator.model.ClassSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @PostMapping("/create-class")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public boolean createClass(@RequestBody ClassSchedule classSchedule, Principal principal) {
        return classScheduleDao.createClass(classSchedule, principal);
    }

    @DeleteMapping("/delete-class/{name}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public boolean deleteClass(@PathVariable String name  ) {
        return  classScheduleDao.deleteClass(name);
    }
}
