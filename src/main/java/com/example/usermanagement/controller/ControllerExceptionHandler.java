package com.example.usermanagement.controller;


import com.example.usermanagement.dto.BaseErrorDto;
import com.example.usermanagement.exception.EmptyFieldsException;
import com.example.usermanagement.exception.InvalidPasswordException;
import com.example.usermanagement.exception.UserAlreadyExistsException;
import com.example.usermanagement.exception.UserNotExistException;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String exceptionMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseErrorDto(exceptionMessage));
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<BaseErrorDto> handleUserNotExistsException(UserNotExistException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<BaseErrorDto> handleUserAlreadyExistsException(UserAlreadyExistsException existsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseErrorDto(existsException.getMessage()));

    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<BaseErrorDto> handlePasswordDoesNotMatchException(InvalidPasswordException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseErrorDto(ex.getMessage()));
    }
    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<BaseErrorDto> handleEmptyFieldException(EmptyFieldsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseErrorDto(ex.getMessage()));
    }


}
