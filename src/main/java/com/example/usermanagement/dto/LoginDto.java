package com.example.usermanagement.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {

    @NotEmpty(message = "Username cannot be null or empty")
    private String username;

    @NotEmpty(message = "Password cannot be null or empty")
    private String password;
}
