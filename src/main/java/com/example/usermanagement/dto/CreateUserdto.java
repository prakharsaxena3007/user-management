package com.example.usermanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserdto {

    private Long id;

    @JsonProperty(value = "username", required = true)
    @NotNull(message = "Username is required!")
    private String username;

    @JsonProperty(value = "password", required = true)
    @NotNull(message = "Password is required!")
    private String password;

    @JsonProperty(value = "first_name", required = true)
    @NotNull(message = "First Name is required!")
    private String firstName;

    @JsonProperty(value = "last_name", required = true)
    @NotNull(message = "Last Name is required!")
    private String lastName;

    @JsonProperty(value = "role", required = true)
    @NotNull(message = "Role is required!")
    private String role;
}
