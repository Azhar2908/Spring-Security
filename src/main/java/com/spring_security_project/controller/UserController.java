package com.spring_security_project.controller;

import com.spring_security_project.model.AppUser;
import com.spring_security_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String helloUser() {
        return "Spring Security Project By Azhar";
    }

    //Insert user:
    @PostMapping("/addUser")
    public ResponseEntity<AppUser> registerUser(@RequestBody AppUser appUser) {
        AppUser createUser = userService.registerUser(appUser);
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }

    //Read all users:
    @GetMapping("/allUsers")
    public ResponseEntity<List<AppUser>> getAllUsers() {
        List<AppUser> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //Read user by id:
    @GetMapping("/getUser/{id}")
    public ResponseEntity<AppUser> getUserById(@PathVariable Long id) {
        AppUser appUser = userService.getUserById(id);
        if (appUser != null) {
            return new ResponseEntity<>(appUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Update user by id:
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<AppUser> updateUserById(@PathVariable Long id,
                                                  @RequestBody AppUser appUser) {
        appUser.setId(id);
        AppUser updateUsers = userService.updateUserById(appUser);
        if (updateUsers != null) {
            return new ResponseEntity<>(updateUsers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Delete user by id:
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        AppUser user = userService.getUserById(id);
        if (user != null) {
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
