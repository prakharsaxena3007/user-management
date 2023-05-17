package com.example.usermanagement.Controller;


import com.example.usermanagement.Model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private List<User> userList = new ArrayList<>();


    //Create user
    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody User user){
        Optional<User> existingUser = findUserByUserName(user.getUserName());
        if(existingUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already Exists");
        }

        user.setPassword(user.getPassword());
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setRole(user.getRole());
        user.setCreatedAt(LocalDateTime.now());
        userList.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User Created");

    }



    //Update a user
    @PutMapping("/{userName}")
    public ResponseEntity<String>updateUser(@PathVariable String userName, @RequestBody User updatedUser) {
        Optional<User> existingUser = findUserByUserName(userName);
        if (existingUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = existingUser.get();
        user.setPassword(updatedUser.getPassword());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setRole(updatedUser.getRole());
        user.setUpdatedAt(LocalDateTime.now());
        return ResponseEntity.ok("User updated successfully");
    }


        //Delete a user
        @DeleteMapping("/{userName}")
        public ResponseEntity<String> deleteUser(@PathVariable String userName) {
            Optional<User> existingUser = findUserByUserName(userName);
            if (existingUser.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            User user = existingUser.get();
            userList.remove(user);
            user.setDeletedAt(LocalDateTime.now());
            return ResponseEntity.ok("User deleted successfully");
        }



        // Get a USer
        @GetMapping("/{username}")
        public ResponseEntity<User> getUser(@PathVariable String username) {
            Optional<User> existingUser = findUserByUserName(username);
            return existingUser.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }


    //Get all user
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userList);
    }


    private Optional<User> findUserByUserName(String userName) {
            return userList.stream()
                    .filter(user -> user.getUserName().equals(userName))
                    .findFirst();
        }


    }


