package com.example.usermanagement.implementation;

import com.example.usermanagement.dto.Response;
import com.example.usermanagement.Model.User;
import com.example.usermanagement.constants.UserConstants;
import com.example.usermanagement.dto.CreateUserdto;
import com.example.usermanagement.dto.UpdateUserDto;
import com.example.usermanagement.exception.UserAlreadyExistsException;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.utility.UserExisting;
import com.example.usermanagement.exception.UserNotExistException;
import com.example.usermanagement.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserExisting userExisting;

    @Autowired
    UserRepository userRepository;

    public Response create(CreateUserdto createUserdto) throws UserAlreadyExistsException {
        String username = createUserdto.getUsername();
        User user = userRepository.findByUsername(username);
        if (user != null) {
            throw new UserAlreadyExistsException(String.format(UserConstants.USER_ALREADY_EXISTS, username));
        }
        user = userMapper.mapToUser(createUserdto);
        userRepository.save(user);
        createUserdto.setId(user.getId());
        return userMapper.createMapper(user);
    }

    public Response update(UpdateUserDto updateUserDto, Long userId) throws UserNotExistException {
        userExisting.ifUserExist(userId);
        Optional<User> user = userRepository.findById(userId);
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
        User userDelete = new User();
        userMapper.deleteUserMapper(user, userDelete);
        userRepository.deleteById(userDelete.getId());
    }

    public List<Response> getAll() {
        List<User> userList = userRepository.findAll();
        return userMapper.getAllUserMapper(userList);
    }
}
