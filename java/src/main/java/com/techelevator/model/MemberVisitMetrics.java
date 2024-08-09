package com.techelevator.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.util.Date;

public class MemberVisitMetrics {

    private int userId;
    private Date dateVisitedGym;
    private Duration totalMinutesSpent;
    private Duration averageTimeSpent;

    public MemberVisitMetrics(int userId, Date dateVisitedGym, Duration totalMinutesSpent, Duration averageTimeSpent) {
        this.userId = userId;
        this.dateVisitedGym = dateVisitedGym;
        this.totalMinutesSpent = totalMinutesSpent;
        this.averageTimeSpent = averageTimeSpent;
    }
    public MemberVisitMetrics() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDateVisitedGym() {
        return dateVisitedGym;
    }

    public void setDateVisitedGym(Date dateVisitedGym) {
        this.dateVisitedGym = dateVisitedGym;
    }

    public Duration getTotalMinutesSpent() {
        return totalMinutesSpent;
    }

    public void setTotalMinutesSpent(Duration totalMinutesSpent) {
        this.totalMinutesSpent = totalMinutesSpent;
    }

    public Duration getAverageTimeSpent() {
        return averageTimeSpent;
    }

    public void setAverageTimeSpent(Duration averageTimeSpent) {
        this.averageTimeSpent = averageTimeSpent;
    }

    public long getTotalMinutes(){
        return totalMinutesSpent.toMinutes();
    }
    public long getTotalHours(){
        return totalMinutesSpent.toHours();
    }
    public long getAverageMinutes(){
        return averageTimeSpent.toMinutes();
    }
    public long getAverageHours(){
        return averageTimeSpent.toHours();
    }
}
