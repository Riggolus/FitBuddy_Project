package com.techelevator.model;

import java.sql.Time;
import java.time.Duration;
import java.util.Date;

public class WorkoutMetrics {

    private int userId;
    private String exerciseName;
    private int sets;
    private int reps;
    private double weight;
    private Date date;
    private Duration duration;

    public WorkoutMetrics(){

    }
    public WorkoutMetrics(int userId, String exerciseName, int sets, int reps, double weight, Date date, Duration duration) {
        this.userId = userId;
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.date = date;
        this.duration = duration;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public long getDurationInMinutes(){
        return duration.toMinutes();
    }
    public long getDurationInHours(){
        return duration.toHours();
    }
}
