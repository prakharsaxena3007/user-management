package com.example.usermanagement.service;


import com.example.usermanagement.dto.*;
import com.example.usermanagement.dto.Response;
import com.example.usermanagement.model.User;
import com.example.usermanagement.constants.UserConstants;
import com.example.usermanagement.exception.PasswordDoesNotMatchException;
import com.example.usermanagement.exception.UserAlreadyExistsException;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.utility.PasswordHelper;
import com.example.usermanagement.utility.UserValidation;
import com.example.usermanagement.exception.UserNotExistException;
import com.example.usermanagement.mapper.UserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidation userExisting;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserService(UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder, UserValidation userExisting, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userExisting = userExisting;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse create(CreateUserdto createUserdto) throws UserAlreadyExistsException {
        User user = userRepository.findByUsername(createUserdto.getUsername());
        if (user != null) {
            throw new UserAlreadyExistsException(String.format(UserConstants.USER_ALREADY_EXISTS, user.getUsername()));
        }
        createUserdto.setPassword(passwordEncoder.encode(createUserdto.getPassword()));
        return userMapper.createUserMapper(userRepository.save(userMapper.mapToUser(createUserdto)));
    }

    public AuthTokenResponse authenticate(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        var user = userRepository.findByUsername(loginDto.getUsername());
        String jwtToken = jwtService.generateToken(user);
        AuthTokenResponse authTokenResponse = new AuthTokenResponse();
        authTokenResponse.setAccessToken(jwtToken);
        return AuthTokenResponse.builder().accessToken(jwtToken).build();
    }

    public Response update(UpdateUserDto updateUserDto, String username) throws UserNotExistException {
        User user = userExisting.ifUserExist(username);
        userMapper.updateMapper(updateUserDto, Optional.of(user));
        return  userMapper.responseToUserMapper(userRepository.save(user));
    }

    public Response getUser(String username) {
        User userexist = userExisting.ifUserExist(username);
        return userMapper.responseToUserMapper(userexist);
    }

    public void deleteUser(String username) {
        userRepository.delete(userExisting.ifUserExist(username));
    }

    public List<Response> getAll() throws UserNotExistException {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            throw new UserNotExistException(UserConstants.NO_USER);
        }
        return userMapper.getAllUserMapper(userList);
    }

    public boolean updatePassword(UserPasswordDto userPasswordDto) throws PasswordDoesNotMatchException {
        User user = userRepository.findByUsername(userPasswordDto.getUsername());
            if (PasswordHelper.matchPassword(userPasswordDto.getOldPassword(), user.getPassword())) {
                String newEncryptedPassword = PasswordHelper.hashPassword(userPasswordDto.getNewPassword());
                user.setPassword(newEncryptedPassword);
                userRepository.save(user);
                return true;
            }else {
                throw new PasswordDoesNotMatchException(UserConstants.PASSWORD_MISMATCH);
            }
    }

}
