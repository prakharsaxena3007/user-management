package com.example.usermanagement.controller;

import com.example.usermanagement.dto.*;
import com.example.usermanagement.model.Response;
import com.example.usermanagement.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    
    @PostMapping(value = "register")
    public ResponseEntity<ResponseDTO> register(@Valid @RequestBody CreateUserDTO createUserdto) {
        return new ResponseEntity<>(userService.create(createUserdto), HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<String> authenticate(@Valid @RequestBody LoginDTO loginDto) {
        String response = userService.authenticate(loginDto);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("logout")
    public ResponseEntity<Response> logout(@RequestHeader("refresh_token")String refreshToken, HttpServletRequest request) {
            return userService.logout(refreshToken,request);
        }
}
