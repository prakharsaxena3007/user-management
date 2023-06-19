package com.example.usermanagement.controller;


import com.example.usermanagement.constants.UserConstants;
import com.example.usermanagement.dto.BaseErrorDTO;
import com.example.usermanagement.exception.InvalidPasswordException;
import com.example.usermanagement.exception.UserAlreadyExistsException;
import com.example.usermanagement.exception.UserNotExistException;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;


@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String exceptionMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseErrorDTO(exceptionMessage));
    }
    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<BaseErrorDTO> handleUserNotExistsException(UserNotExistException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseErrorDTO(ex.getMessage()));
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<BaseErrorDTO> handleUserAlreadyExistsException(UserAlreadyExistsException existsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseErrorDTO(existsException.getMessage()));
    }
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<BaseErrorDTO> handlePasswordDoesNotMatchException(InvalidPasswordException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseErrorDTO(ex.getMessage()));
    }
    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<BaseErrorDTO> handleUnexpectedTypeException(UnexpectedTypeException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseErrorDTO(ex.getMessage()));
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseErrorDTO> handleUnexpectedTypeException(HttpMessageNotReadableException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseErrorDTO(UserConstants.NO_NULL_ROLE));
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<BaseErrorDTO> handleUnauthorisedException(AuthenticationException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BaseErrorDTO(UserConstants.UNAUTHORISED));
    }
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<BaseErrorDTO> handleWebClientResponseException(WebClientResponseException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BaseErrorDTO(UserConstants.UNAUTHORISED));
    }
}
