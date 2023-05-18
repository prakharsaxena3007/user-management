package com.example.usermanagement.Model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseData {

    private String username;
    private String firstName;
    private String lastName;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
