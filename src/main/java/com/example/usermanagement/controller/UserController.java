package com.example.usermanagement.controller;

import com.example.usermanagement.dto.ResponseDTO;
import com.example.usermanagement.dto.UpdateUserDTO;
import com.example.usermanagement.dto.UserPasswordDTO;
import com.example.usermanagement.model.User;
import com.example.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateUser(@PathVariable Long id, @RequestBody UpdateUserDTO updateUserDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(updateUserDto, id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(id));
    }
    @GetMapping("/all-users")
    public ResponseEntity<List<ResponseDTO>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }
    @PutMapping("/update_password")
    public ResponseEntity<String> updatePassword(@RequestBody UserPasswordDTO userPasswordDto) {
        userService.updatePassword(userPasswordDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping
    public ResponseEntity<List<ResponseDTO>> fetchUser() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody User user) {
        log.info("Added new User {}", user);
    }

    @PutMapping
    public User updateUserDetails(@RequestBody User user) {
        log.info("Update User details {}", user);
        return user;
    }
}



