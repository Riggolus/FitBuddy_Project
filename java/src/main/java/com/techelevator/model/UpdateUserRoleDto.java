package com.techelevator.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UpdateUserRoleDto {

    @NotNull
    private int userId;
    @NotEmpty
    private String role;
    @NotEmpty
    private String username;

    public UpdateUserRoleDto( int userId, String role, String username){
        this.userId = userId;
        this.role = role;
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
