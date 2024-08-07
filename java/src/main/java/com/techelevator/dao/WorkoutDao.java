package com.techelevator.dao;

import com.techelevator.model.Workout;
import com.techelevator.model.WorkoutDto;

import java.security.Principal;
import java.util.Date;
import java.util.List;

public interface WorkoutDao {

    void addWorkout(WorkoutDto workoutDto, Principal principal);
    Workout getLastWorkoutByUserId(int userId);
    List<WorkoutDto> getWorkoutByDate(Date dateOfWorkout, Principal principal);
    List<Workout> getAllWorkouts(int userId);
}
