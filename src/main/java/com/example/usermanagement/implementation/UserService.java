package com.example.usermanagement.implementation;

import com.example.usermanagement.dto.Response;
import com.example.usermanagement.Model.User;
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
            throw new UserAlreadyExistsException(String.format(UserConstants.USER_ALREADY_EXISTS, user));
        }
        user = userMapper.mapToUser(createUserdto);
        userRepository.save(user);
        return userMapper.createMapper(user);
    }

    public Response update(UpdateUserDto updateUserDto, Long userId) throws UserNotExistException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new UserNotExistException(UserConstants.USER_NOT_FOUND);
        }
        userRepository.save(user.orElseThrow(() -> new UserNotExistException(UserConstants.USER_NOT_FOUND)));
        return userMapper.updateMapper(updateUserDto, user);
    }

    public Response getUser(Long userId) throws UserNotExistException {
        userExisting.ifUserExist(userId);
        return userMapper.getUserMapper(userRepository.findById(userId));
    }

    public void deleteUser(Long userId) throws UserNotExistException {
        Optional<User> user = userRepository.findById(userId);
        userExisting.ifUserExist(userId);
        userMapper.deleteUserMapper(user);
        userRepository.deleteById(userId);
    }

    public List<Response> getAll() {
        List<User> userList = userRepository.findAll();
        return userMapper.getAllUserMapper(userList);
    }
}
