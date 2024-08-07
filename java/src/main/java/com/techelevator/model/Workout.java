package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Workout {

    private int workoutId;
    private int userId;
    private int exerciseId;
    private int sets;
    private int reps;
    private double weight;
    private int duration;

    private Date dateOfWorkout;

    public Workout(int workoutId, int userId, int exerciseId, int sets, int reps, double weight, int duration, Date dateOfWorkout) {
        this.workoutId = workoutId;
        this.userId = userId;
        this.exerciseId = exerciseId;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.duration = duration;
        this.dateOfWorkout = dateOfWorkout;
    }
    public Workout(){

    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getDateOfWorkout() {
        return dateOfWorkout;
    }

    public void setDateOfWorkout(Date dateOfWorkout) {
        this.dateOfWorkout = dateOfWorkout;
    }
}
