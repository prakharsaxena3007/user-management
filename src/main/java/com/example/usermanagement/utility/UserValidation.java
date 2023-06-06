package com.example.usermanagement.utility;

import com.example.usermanagement.constants.UserConstants;
import com.example.usermanagement.exception.UserNotExistException;
import com.example.usermanagement.model.User;
import com.example.usermanagement.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserValidation {

    private final UserRepository userRepository;

    public UserValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User ifUserExist(Long id) throws UserNotExistException {
        return userRepository.findAll().stream()
                .filter(user -> user.getId().equals(id)).findFirst()
                .orElseThrow(() -> new UserNotExistException(String.format(UserConstants.USERID_NOT_FOUND, id)));
    }
}

