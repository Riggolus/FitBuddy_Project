package com.techelevator.controller;


import com.techelevator.dao.WorkoutDao;
import com.techelevator.model.Workout;
import com.techelevator.model.WorkoutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/workout")
@PreAuthorize("isAuthenticated()")
public class WorkoutController {

    private final WorkoutDao workoutDao;

    @Autowired
    public WorkoutController(WorkoutDao workoutDao) {
        this.workoutDao = workoutDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public void createWorkout(@RequestBody WorkoutDto workoutDto, Principal principal) {
        workoutDao.addWorkout(workoutDto, principal);
    }

    @GetMapping("/last-workout/{userId}")
    public Workout getLastWorkoutByUserId(@PathVariable int userId) {
        return workoutDao.getLastWorkoutByUserId(userId);
    }

    @GetMapping("/date/{date}")
    public List<Workout>  getWorkoutByDate(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return workoutDao.getWorkoutByDate(date);
    }

    @GetMapping("/history/{userId}")
    public List<Workout> getAllWorkouts(@PathVariable int userId) {
        return workoutDao.getAllWorkouts(userId);
    }

}
