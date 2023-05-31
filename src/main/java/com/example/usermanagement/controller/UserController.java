package com.example.usermanagement.controller;

import com.example.usermanagement.dto.Response;
import com.example.usermanagement.dto.UpdateUserDto;
import com.example.usermanagement.dto.UserPasswordDto;
import com.example.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/users")
public class UserController {


    @Autowired
    UserService userService;

    //Update a user
    @PutMapping("/{username}")
    public ResponseEntity<Response> updateUser(@PathVariable String username, @RequestBody UpdateUserDto updateUserDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(updateUserDto, username));
    }

    //Delete a user
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Get a USer
    @GetMapping("/{username}")
    public ResponseEntity<Response> getUser(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(username));
    }

    //Get all user
    @GetMapping
    public ResponseEntity<List<Response>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    @PostMapping("/update_password")
    public ResponseEntity<String> updatePassword(@RequestBody UserPasswordDto userPasswordDto) {
        userService.updatePassword(userPasswordDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}


