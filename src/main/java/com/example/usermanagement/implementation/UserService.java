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
            throw new UserAlreadyExistsException(String.format(UserConstants.USER_ALREADY_EXISTS, user.getUsername()));
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
        userRepository.save(user.get());
        return userMapper.updateMapper(updateUserDto, user);
    }

    public Response getUser(Long userId){
        User userexist = userExisting.ifUserExist(userId);
        return userMapper.getUserMapper(Optional.of(userexist));
    }

    public void deleteUser(Long userId){
        Optional<User> user = Optional.of(userExisting.ifUserExist(userId));
        userMapper.deleteUserMapper(user);
        userRepository.deleteById(userId);
    }

    public List<Response> getAll() throws UserNotExistException {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            throw new UserNotExistException(UserConstants.NO_USER);
        }
        return userMapper.getAllUserMapper(userList);
    }
}
