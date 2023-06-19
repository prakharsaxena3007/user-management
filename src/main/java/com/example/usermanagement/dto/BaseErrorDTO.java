package com.example.usermanagement.dto;

import lombok.Data;

@Data
public class BaseErrorDTO {

    private String message;
    public BaseErrorDTO(String message) {
        this.message = message;
    }
}
