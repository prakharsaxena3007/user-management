package com.example.usermanagement.service;

import com.example.usermanagement.dto.Response;
import com.example.usermanagement.model.User;
import com.example.usermanagement.constants.UserConstants;
import com.example.usermanagement.dto.CreateUserdto;
import com.example.usermanagement.dto.UpdateUserDto;
import com.example.usermanagement.exception.UserAlreadyExistsException;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.utility.UserValidation;
import com.example.usermanagement.exception.UserNotExistException;
import com.example.usermanagement.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserValidation userExisting;

    @Autowired
    UserRepository userRepository;

    public Response create(CreateUserdto createUserdto) throws UserAlreadyExistsException {
        User user = userRepository.findByUsername(createUserdto.getUsername());
        if (user != null) {
            throw new UserAlreadyExistsException(String.format(UserConstants.USER_ALREADY_EXISTS, user.getUsername()));
        }
        user = userMapper.mapToUser(createUserdto);
        userRepository.save(user);
        return userMapper.responseToUserMapper(user);
    }

    public Response update(UpdateUserDto updateUserDto, Long userId) throws UserNotExistException {
        User user = userExisting.ifUserExist(userId);
        userMapper.updateMapper(updateUserDto, Optional.of(user));
        return userMapper.responseToUserMapper(userRepository.save(user));
    }

    public Response getUser(Long userId){
        return userMapper.responseToUserMapper(userExisting.ifUserExist(userId));
    }

    public void deleteUser(Long userId){
        userRepository.delete(userExisting.ifUserExist(userId));
    }

    public List<Response> getAll() throws UserNotExistException {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            throw new UserNotExistException(UserConstants.NO_USER);
        }
        return userMapper.getAllUserMapper(userList);
    }
}
