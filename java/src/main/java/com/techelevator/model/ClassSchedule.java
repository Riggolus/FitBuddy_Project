package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.crypto.Data;
import java.time.LocalTime;
import java.util.Date;

public class ClassSchedule {

    private String className;
    private String instructorName;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
    @JsonProperty("isMonday")
    private boolean isMonday;
    @JsonProperty("isTuesday")
    private boolean isTuesday;
    @JsonProperty("isWednesday")
    private boolean isWednesday;
    @JsonProperty("isThursday")
    private boolean isThursday;
    @JsonProperty("isFriday")
    private boolean isFriday;
    @JsonProperty("isSaturday")
    private boolean isSaturday;
    @JsonProperty("isSunday")
    private boolean isSunday;
    private Date recurrenceEnd;
    private int createdBy;
    private Date createdAt;

    public ClassSchedule(String className, String instructorName, LocalTime startTime, LocalTime endTime, String description, boolean isMonday, boolean isTuesday,
                         boolean isWednesday, boolean isFriday, boolean isThursday, boolean isSaturday, boolean isSunday,
                         Date recurrenceEnd, int createdBy, Date createdAt) {
        this.className = className;
        this.instructorName = instructorName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.isMonday = isMonday;
        this.isTuesday = isTuesday;
        this.isWednesday = isWednesday;
        this.isFriday = isFriday;
        this.isThursday = isThursday;
        this.isSaturday = isSaturday;
        this.isSunday = isSunday;
        this.recurrenceEnd = recurrenceEnd;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public ClassSchedule() {

    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public boolean isMonday() {
        return isMonday;
    }

    public void setMonday(boolean monday) {
        isMonday = monday;
    }

    public boolean isTuesday() {
        return isTuesday;
    }

    public void setTuesday(boolean tuesday) {
        isTuesday = tuesday;
    }

    public boolean isWednesday() {
        return isWednesday;
    }

    public void setWednesday(boolean wednesday) {
        isWednesday = wednesday;
    }

    public boolean isFriday() {
        return isFriday;
    }

    public void setFriday(boolean friday) {
        isFriday = friday;
    }

    public boolean isThursday() {
        return isThursday;
    }

    public void setThursday(boolean thursday) {
        isThursday = thursday;
    }

    public boolean isSaturday() {
        return isSaturday;
    }

    public void setSaturday(boolean saturday) {
        isSaturday = saturday;
    }

    public boolean isSunday() {
        return isSunday;
    }

    public void setSunday(boolean sunday) {
        isSunday = sunday;
    }

    public Date getRecurrenceEnd() {
        return recurrenceEnd;
    }

    public void setRecurrenceEnd(Date recurrenceEnd) {
        this.recurrenceEnd = recurrenceEnd;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
