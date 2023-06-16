package com.example.usermanagement.controller;


import com.example.usermanagement.dto.*;
import com.example.usermanagement.model.IntrospectResponse;
import com.example.usermanagement.model.Response;
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
//    private final CustomKeycloakLogoutHandler logoutHandler;




    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "register")
    public ResponseEntity<ResponseDto> register(@Valid @RequestBody CreateUserDto createUserdto) {
        return new ResponseEntity<>(userService.create(createUserdto), HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<String> authenticate(@Valid
                                                   @RequestBody LoginDto loginDto) {
        String tokenResponse = userService.authenticate(loginDto);
        if (tokenResponse != null) {
            return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("logout")
    public ResponseEntity<Response> logout(@RequestBody TokenRequestDto token) {
            return userService.logout(token);
        }

    @PostMapping("/introspect")
    public ResponseEntity<IntrospectResponse> introspect(@RequestBody TokenRequestDto token) {
        return userService.introspect(token);
    }

    }
