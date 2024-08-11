package com.techelevator.model;

import java.util.Date;

public class ClassDto {

    private Date sessoinDate;
    private String className;

    public ClassDto(Date sessoinDate, String className) {
        this.sessoinDate = sessoinDate;
        this.className = className;
    }

    public Date getSessoinDate() {
        return sessoinDate;
    }

    public void setSessoinDate(Date sessoinDate) {
        this.sessoinDate = sessoinDate;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
