package com.example.usermanagement.dto;

import lombok.Data;

@Data
public class BaseErrorDto {

    private String message;

    public BaseErrorDto(String message) {
        this.message = message;
    }
}
