package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Achievements {
    private int goalId;
    private int userId;
    private String goalDescription;
    @JsonProperty("completed")
    private boolean completed;

    public Achievements(){}
    public Achievements(int goalId, int userId, String goalDescription, boolean completed) {
        this.goalId = goalId;
        this.userId = userId;
        this.goalDescription = goalDescription;
        this.completed = completed;
    }

    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getGoalDescription() {
        return goalDescription;
    }

    public void setGoalDescription(String goalDescription) {
        this.goalDescription = goalDescription;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
