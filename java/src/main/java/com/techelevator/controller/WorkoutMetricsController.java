package com.techelevator.controller;

import com.techelevator.dao.WorkoutDao;
import com.techelevator.dao.WorkoutMetricDao;
import com.techelevator.model.Workout;
import com.techelevator.model.WorkoutMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/metrics")
public class WorkoutMetricsController {


    private final WorkoutMetricDao workoutMetricDao;

    @Autowired
    public WorkoutMetricsController(WorkoutMetricDao workoutMetricDao){
        this.workoutMetricDao = workoutMetricDao;
    }

    @GetMapping
    public List<WorkoutMetrics> myMetrics(Principal principal){
        return workoutMetricDao.myMetrics(principal);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public List<WorkoutMetrics> memberMetrics(@PathVariable int userId){
        return workoutMetricDao.memberMetrics(userId);
    }
}
