package com.example.usermanagement.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;



import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class User {



    @JsonProperty(value = "username" ,required = true)
    @NotNull(message = "Username Cannot be Empty!!")
    private String userName;
    @JsonProperty(value = "password" ,required = true)
    @NotNull(message = "Password Cannot be Empty!!")
    private String password;
    @JsonProperty(value = "firstName" ,required = true)
    @NotNull(message = "First Name Cannot be Empty!!")
    private String firstName;
    @JsonProperty(value = "lastName" ,required = true)
    @NotNull(message = "LastName Cannot be Empty!!")
    private String lastName;
    @JsonProperty(value = "role" ,required = true)
    @NotNull(message = "Role Cannot be Empty!!")
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public User(String userName, String password, String firstName, String lastName, String role) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
