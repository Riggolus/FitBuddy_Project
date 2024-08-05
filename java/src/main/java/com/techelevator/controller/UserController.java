package com.techelevator.controller;


import com.techelevator.dao.UserDao;
import com.techelevator.model.UpdateUserRoleDto;
import com.techelevator.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
@PreAuthorize("isAuthenticated()")
public class UserController {

    private final UserDao userDao;


    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public List<UpdateUserRoleDto> getUsers(){
        List<UpdateUserRoleDto> users = userDao.getUsers();
        return users;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/update-role")
    public boolean updateUserRole(@RequestBody UpdateUserRoleDto updateUserRoleDto) {
        boolean user = userDao.updateUserRole(updateUserRoleDto.getUserId(), updateUserRoleDto.getRole());
        return user;
    }

}
