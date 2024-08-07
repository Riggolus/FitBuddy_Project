package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class WorkoutDto {

    private int exerciseId;
    private int sets;
    private int reps;
    private double weight;
    private Integer duration;
    private Date dateOfWorkout;
    private String exerciseName;
    private int userId;
    public WorkoutDto(){

    }

    public WorkoutDto( int exerciseId, int sets, int reps, double weight, Integer duration, Date dateOfWorkout, String exerciseName) {
        this.exerciseId = exerciseId;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.duration = duration;
        this.dateOfWorkout = dateOfWorkout;
        this.exerciseName = exerciseName;
    }
    public WorkoutDto( int exerciseId, int sets, int reps, double weight, Integer duration, Date dateOfWorkout) {
        this.exerciseId = exerciseId;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.duration = duration;
        this.dateOfWorkout = dateOfWorkout;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getDateOfWorkout() {
        return dateOfWorkout;
    }

    public void setDateOfWorkout(Date dateOfWorkout) {
        this.dateOfWorkout = dateOfWorkout;
    }
}
