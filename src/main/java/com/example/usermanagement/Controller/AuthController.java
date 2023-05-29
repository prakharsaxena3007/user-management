package com.example.usermanagement.Controller;

import com.example.usermanagement.dto.AuthTokenResponse;
import com.example.usermanagement.dto.AuthenticationResponse;
import com.example.usermanagement.dto.CreateUserdto;
import com.example.usermanagement.dto.LoginDto;
import com.example.usermanagement.implementation.UserService;
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
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody CreateUserdto createUserdto) {
        return new ResponseEntity<>(userService.create(createUserdto), HttpStatus.CREATED);
    }

    @PostMapping("authenticate")
    public ResponseEntity<AuthTokenResponse> authenticate(
            @RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(userService.authenticate(loginDto), HttpStatus.ACCEPTED);
    }
}
