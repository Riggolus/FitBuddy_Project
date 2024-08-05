package com.techelevator.dao;

import com.techelevator.model.RegisterUserDto;
import com.techelevator.model.UpdateUserRoleDto;
import com.techelevator.model.User;

import java.util.List;

public interface UserDao {

    List<UpdateUserRoleDto> getUsers();

    User getUserById(int id);

    User getUserByUsername(String username);

    User createUser(RegisterUserDto user);

    boolean updateUserRole(int userId, String role);
}
