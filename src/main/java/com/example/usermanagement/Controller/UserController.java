package com.example.usermanagement.Controller;


import com.example.usermanagement.Model.Response;
import com.example.usermanagement.Model.User;
import com.example.usermanagement.constants.UserConstants;
import com.example.usermanagement.dto.CreateUserdto;
import com.example.usermanagement.dto.UpdateUserDto;
import com.example.usermanagement.exception.CustomException;
import com.example.usermanagement.implementation.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private List<User> userList = new ArrayList<>();

    @Autowired
    UserService userService;

    //Create user
    @PostMapping()
    public ResponseEntity<Response> createUser(@Valid @RequestBody CreateUserdto createUserdto) throws CustomException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(createUserdto, userList));
    }


    //Update a user
    @PutMapping("/{username}")
    public ResponseEntity<Response> updateUser(@PathVariable String username, @RequestBody UpdateUserDto updateUserDto) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(updateUserDto, userList, username));
    }

    //Delete a user
    @DeleteMapping("/{username}")
    public ResponseEntity<Response> deleteUser(@PathVariable String username) throws CustomException {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userService.deleteUser(username, userList));
    }

    // Get a USer
    @GetMapping("/{username}")
    public ResponseEntity<Response> getUser(@PathVariable String username) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(username, userList));
    }

    //Get all user
    @GetMapping
    public ResponseEntity<List<Response>> getAllUsers() throws CustomException {
        List<Response> usersList = userService.getAll(userList);
        if (userList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(usersList);
        }
        return ResponseEntity.status(HttpStatus.OK).body(usersList);
    }
}


