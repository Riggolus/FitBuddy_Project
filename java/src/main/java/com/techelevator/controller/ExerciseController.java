package com.techelevator.controller;

import com.techelevator.dao.ExerciseDao;
import com.techelevator.model.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping("/exercise")
public class ExerciseController {

    private final ExerciseDao exerciseDao;
    @Autowired
    public ExerciseController (ExerciseDao exerciseDao){
        this.exerciseDao = exerciseDao;
    }
    @GetMapping("/all")
    public List<Exercise> getExercises(){
        return exerciseDao.getExercises();
    }
}
