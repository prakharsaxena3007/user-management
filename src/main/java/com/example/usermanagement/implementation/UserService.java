package com.example.usermanagement.implementation;

import com.example.usermanagement.Model.Response;
import com.example.usermanagement.Model.User;
import com.example.usermanagement.constants.UserConstants;
import com.example.usermanagement.dto.CreateUserdto;
import com.example.usermanagement.dto.UpdateUserDto;
import com.example.usermanagement.exception.UserAlreadyExistsException;
import com.example.usermanagement.exception.UserExisting;
import com.example.usermanagement.exception.UserNotExistException;
import com.example.usermanagement.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserExisting userExisting;

    public Response create(CreateUserdto createUserdto, List<User> userList) throws UserAlreadyExistsException {
        for (User user : userList) {
            if (user.getUserName().equals(createUserdto.getUsername())) {
                throw new UserAlreadyExistsException(String.format(UserConstants.USER_ALREADY_EXISTS, createUserdto.getUsername()));
            }
        }
        return userMapper.createMapper(createUserdto, userList);
    }

    public Response update(UpdateUserDto updateUserDto, List<User> userList, String username) throws UserNotExistException {
        userExisting.ifUserExist(userList, username);
        return userMapper.updateMapper(updateUserDto, userList, username);
    }

    public Response getUser(String username, List<User> userList) throws UserNotExistException {
        userExisting.ifUserExist(userList, username);
        return userMapper.getUserMapper(username, userList);
    }

    public Response deleteUser(String username, List<User> userList) throws UserNotExistException {
        userExisting.ifUserExist(userList, username);
        return userMapper.deleteUserMapper(username, userList);

    }

    public List<Response> getAll(List<User> userList) {
        return userMapper.getAllUserMapper(userList);
    }
}
