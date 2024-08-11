package com.techelevator.model;

import java.security.Timestamp;
import java.util.Date;

public class ClassRegistration {

    private Date sessionDate;
    private int totalCapacity;
    private int currentCapacity;
    private String className;
    private String description;
    private Date registrationDate;
    private String instructorName;
    private int classId;

    public ClassRegistration(){

    }
    public ClassRegistration(Date sessionDate, int totalCapacity, int currentCapacity, String className, String description, Date registrationDate, String instructorName, int classId) {
        this.sessionDate = sessionDate;
        this.totalCapacity = totalCapacity;
        this.currentCapacity = currentCapacity;
        this.className = className;
        this.description = description;
        this.registrationDate = registrationDate;
        this.instructorName = instructorName;
        this.classId = classId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }
}
