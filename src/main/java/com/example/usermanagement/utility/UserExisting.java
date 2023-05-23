package com.example.usermanagement.utility;

import com.example.usermanagement.Model.User;
import com.example.usermanagement.constants.UserConstants;
import com.example.usermanagement.exception.UserNotExistException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserExisting {
    public User ifUserExist(List<User> userList, String username) throws UserNotExistException {
        return userList.stream()
                .filter(user -> user.getUsername().equals(username)).findFirst()
                .orElseThrow(() -> new UserNotExistException(String.format(UserConstants.USER_NOT_FOUND, username)));
    }

}

