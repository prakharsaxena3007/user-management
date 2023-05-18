package com.example.usermanagement.Controller;


import com.example.usermanagement.Model.Response;
import com.example.usermanagement.Model.User;
import com.example.usermanagement.constants.UserConstants;
import com.example.usermanagement.exception.CustomException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private List<User> userList = new ArrayList<>();

    private final UserConstants userConstants = new UserConstants();

    //Create user
    @PostMapping()
    public ResponseEntity<Response> createUser(@Valid @RequestBody User user) throws CustomException {
        Optional<User> existingUser = findUserByUserName(user.getUserName());
        try {
            if (existingUser.isPresent()) {

                throw new CustomException(String.format(userConstants.USER_NOT_FOUND, user.getUserName()));
            }

            user.setPassword(user.getPassword());
            user.setFirstName(user.getFirstName());
            user.setLastName(user.getLastName());
            user.setRole(user.getRole());
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            userList.add(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(new Response(String.format(userConstants.USER_CREATED, user.getUserName())));

        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response(e.getMessage()));
        }
    }

    //Update a user
    @PutMapping("/{username}")
    public ResponseEntity<Response> updateUser(@PathVariable String username, @RequestBody User updatedUser) throws CustomException {
        Optional<User> existingUser = findUserByUserName(username);
        try {
            if (existingUser.isEmpty()) {

                throw new CustomException(String.format(userConstants.USER_NOT_FOUND, username));

            }
            User user = existingUser.get();
            user.setPassword(updatedUser.getPassword());
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setRole(updatedUser.getRole());
            user.setUpdatedAt(LocalDateTime.now());
            return ResponseEntity.ok(new Response(String.format(userConstants.USER_UPDATED, username)));
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(e.getMessage()));
        }
    }

    //Delete a user
    @DeleteMapping("/{username}")
    public ResponseEntity<Response> deleteUser(@PathVariable String username) throws CustomException {
        Optional<User> existingUser = findUserByUserName(username);
        try {
            if (existingUser.isEmpty()) {
                throw new CustomException(String.format(userConstants.USER_NOT_FOUND, username));
            }
            User user = existingUser.get();
            userList.remove(user);
            user.setDeletedAt(LocalDateTime.now());
            return ResponseEntity.ok(new Response((String.format(userConstants.USER_DELETED, username))));
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(e.getMessage()));
        }
    }

    // Get a USer
    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) throws CustomException {
        Optional<User> existingUser = findUserByUserName(username);
        try {
            return existingUser.map(ResponseEntity::ok).orElseThrow(() -> new CustomException(String.format(userConstants.USER_NOT_FOUND, username)));
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //Get all user
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() throws CustomException {
        try {
            if (userList.isEmpty()) {
                throw new CustomException(userConstants.NO_USER);
            }
            return ResponseEntity.ok(userList);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    private Optional<User> findUserByUserName(String userName) {
        return userList.stream()
                .filter(user -> user.getUserName().equals(userName))
                .findFirst();
    }


}


