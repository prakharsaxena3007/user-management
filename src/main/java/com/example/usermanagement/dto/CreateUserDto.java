package com.example.usermanagement.dto;

import com.example.usermanagement.model.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateUserDto {

    @NotEmpty(message = "Username is required!")
    @JsonProperty(value = "username", required = true)
    private String username;

    @JsonProperty(value = "password", required = true)
    @NotEmpty(message = "Password is required!")
    private String password;

    @JsonProperty(value = "first_name", required = true)
    @NotEmpty(message = "First Name is required!")
    private String firstName;

    @JsonProperty(value = "last_name", required = true)
    @NotEmpty(message = "Last Name is required!")
    private String lastName;

    @JsonProperty(value = "role", required = true)
    @NotEmpty(message = "Role is required")
    private Role role = Role.USER;

}
