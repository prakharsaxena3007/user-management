package com.example.usermanagement.controller;

import com.example.usermanagement.dto.*;
import com.example.usermanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "register")
    public ResponseEntity<ResponseDto> register(@Valid @RequestBody CreateUserDto createUserdto) {
        return new ResponseEntity<>(userService.create(createUserdto), HttpStatus.CREATED);
    }

    @PostMapping("authenticate")
    public ResponseEntity<AuthTokenResponseDto> authenticate(@Valid
            @RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(userService.authenticate(loginDto), HttpStatus.OK);
    }
}
