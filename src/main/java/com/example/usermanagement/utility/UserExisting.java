package com.example.usermanagement.utility;

import com.example.usermanagement.Model.User;
import com.example.usermanagement.constants.UserConstants;
import com.example.usermanagement.exception.UserNotExistException;
import com.example.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserExisting {

    @Autowired
    UserRepository userRepository;
    public User ifUserExist(Long userId) throws UserNotExistException {
        return userRepository.findAll().stream()
                .filter(user -> user.getId().equals(userId)).findFirst()
                .orElseThrow(() -> new UserNotExistException(String.format(UserConstants.USER_NOT_FOUND, userId)));
    }

}

