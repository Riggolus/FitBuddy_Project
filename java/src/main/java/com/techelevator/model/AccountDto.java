package com.techelevator.model;

import java.time.LocalDateTime;
import java.util.Date;

public class AccountDto {
    private int userId;
    //the account's username
    private String username;
    //the user's e-mail address
    private String email;
    //the user's first name
    private String firstName;
    //the user's last name
    private String lastName;
    //the user's picture
    private String profilePicture;
    //description of the user
    private String profile;
    //the user's workout goals
    private String goals;
    //day and time the account was created
    private Date createdAt;

    public AccountDto(int userId, String username, String email, String firstName,
                   String lastName, String profilePicture, String profile, String goals, Date createdAt){

        this.userId = userId;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePicture=profilePicture;
        this.profile= profile;
        this.goals = goals;
        this.createdAt = createdAt;


    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }




}
