package com.exam.controller;

import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    //creating user
    @PostMapping("/")
    public User createUser(@RequestBody User user){

        Set<UserRole> roles = new HashSet<>();

        Role role = new Role();
        role.setRoleId(45l);
        role.setRoleName("STUDENT");

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        roles.add(userRole);

        try {
            return this.userService.createUser(user,roles);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username){

        return this.userService.getUser(username);
    }

    @DeleteMapping("/{userId}")
    public void deletUser(@PathVariable Long userId){
        this.userService.deletUser(userId);
    }

    @PutMapping("/{userId}")
    public void updateUser(@PathVariable Long userId, @RequestBody User updatedUser){


        this.userService.updateUser(updatedUser);

    }

}
