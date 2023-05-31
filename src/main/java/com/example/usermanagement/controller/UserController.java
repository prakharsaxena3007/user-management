package com.example.usermanagement.controller;


import com.example.usermanagement.dto.Response;
import com.example.usermanagement.dto.CreateUserdto;
import com.example.usermanagement.dto.UpdateUserDto;
import com.example.usermanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    UserService userService;

    //Create user
    @PostMapping()
    public ResponseEntity<Response> createUser(@Valid @RequestBody CreateUserdto createUserdto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(createUserdto));
    }

    //Update a user
    @PutMapping("/{userId}")
    public ResponseEntity<Response> updateUser(@PathVariable Long userId, @RequestBody UpdateUserDto updateUserDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(updateUserDto, userId));
    }

    //Delete a user
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Get a USer
    @GetMapping("/{userId}")
    public ResponseEntity<Response> getUser(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userId));
    }

    //Get all user
    @GetMapping
    public ResponseEntity<List<Response>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }
}


