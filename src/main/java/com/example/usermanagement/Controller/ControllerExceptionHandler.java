package com.example.usermanagement.Controller;


import com.example.usermanagement.exception.PasswordDoesNotMatchException;
import com.example.usermanagement.exception.UserAlreadyExistsException;
import com.example.usermanagement.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumemtNotValidException(MethodArgumentNotValidException ex) {
        String exceptionMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exceptionMessage);
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<String> handleUserNotExistsException(UserNotExistException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException existsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(existsException.getMessage());

    }

    @ExceptionHandler(PasswordDoesNotMatchException.class)
    public ResponseEntity<String> handlePasswordDoesNotMatchException(PasswordDoesNotMatchException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


}
