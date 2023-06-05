package com.example.usermanagement.service;


import com.example.usermanagement.dto.*;
import com.example.usermanagement.dto.ResponseDto;
import com.example.usermanagement.exception.EmptyFieldsException;
import com.example.usermanagement.exception.InvalidPasswordException;
import com.example.usermanagement.model.User;
import com.example.usermanagement.constants.UserConstants;
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

    public ResponseDto create(CreateUserDto createUserdto) throws UserAlreadyExistsException, EmptyFieldsException {
        User user = userRepository.findByUsername(createUserdto.getUsername());
        if (user != null) {
            throw new UserAlreadyExistsException(String.format(UserConstants.USER_ALREADY_EXISTS, user.getUsername()));
        }
        createUserdto.setPassword(passwordEncoder.encode(createUserdto.getPassword()));
        return userMapper.responseToUserMapper(userRepository.save(userMapper.mapToUser(createUserdto)));
    }

    public AuthTokenResponseDto authenticate(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        User user = userRepository.findByUsername(loginDto.getUsername());
        String jwtToken = jwtService.generateToken(user);
        return AuthTokenResponseDto.builder().accessToken(jwtToken).build();
    }

    public ResponseDto update(UpdateUserDto updateUserDto, Long id) throws UserNotExistException {
        User user = userExisting.ifUserExist(id);
        userMapper.updateMapper(updateUserDto, Optional.of(user));
        return  userMapper.responseToUserMapper(userRepository.save(user));
    }

    public ResponseDto getUser(Long id) {
        User userExists = userExisting.ifUserExist(id);
        return userMapper.responseToUserMapper(userExists);
    }

    public void deleteUser(Long id) {
        userRepository.delete(userExisting.ifUserExist(id));
    }

    public List<ResponseDto> getAll() throws UserNotExistException {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            throw new UserNotExistException(UserConstants.NO_USER);
        }
        return userMapper.getAllUserMapper(userList);
    }

    public void updatePassword(UserPasswordDto userPasswordDto) throws InvalidPasswordException {
        User user = userRepository.findByUsername(userPasswordDto.getUsername());
            if (PasswordHelper.matchPassword(userPasswordDto.getOldPassword(), user.getPassword())) {
                String newEncryptedPassword = PasswordHelper.hashPassword(userPasswordDto.getNewPassword());
                user.setPassword(newEncryptedPassword);
                userRepository.save(user);
            }else {
                throw new InvalidPasswordException(UserConstants.INVALID_PASSWORD);
            }
    }

}
