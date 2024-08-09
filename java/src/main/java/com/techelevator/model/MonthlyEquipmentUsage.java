package com.techelevator.model;

public class MonthlyEquipmentUsage {


    private String exerciseName;
    private Integer usageCount;
    private double totalLoadVolume;
    private int reps;
    private Integer totalDuration;
    private double weight;

    public MonthlyEquipmentUsage(String exerciseName, Integer usageCount, double totalLoadVolume, int reps, Integer totalDuration, double weight) {
        this.exerciseName = exerciseName;
        this.usageCount = usageCount;
        this.totalLoadVolume = totalLoadVolume;
        this.reps = reps;
        this.totalDuration = totalDuration;
        this.weight = weight;
    }

    public MonthlyEquipmentUsage(){

    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public Integer getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }

    public double getTotalLoadVolume() {
        return totalLoadVolume;
    }

    public void setTotalLoadVolume(double totalLoadVolume) {
        this.totalLoadVolume = totalLoadVolume;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public Integer getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Integer totalDuration) {
        this.totalDuration = totalDuration;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
