package com.example.usermanagement.controller;

import com.example.usermanagement.dto.ResponseDto;
import com.example.usermanagement.dto.UpdateUserDto;
import com.example.usermanagement.dto.UserPasswordDto;
import com.example.usermanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Update a user
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto updateUserDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(updateUserDto, id));
    }

    //Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Get a USer
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(id));
    }

    //Get all user
    @GetMapping
    public ResponseEntity<List<ResponseDto>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    @PutMapping("/update_password")
    public ResponseEntity<String> updatePassword(@RequestBody UserPasswordDto userPasswordDto) {
        userService.updatePassword(userPasswordDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}


