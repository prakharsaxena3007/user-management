package com.example.usermanagement.service;

import com.example.usermanagement.dto.*;
import com.example.usermanagement.exception.UserAlreadyExistsException;
import com.example.usermanagement.exception.UserNotExistException;
import com.example.usermanagement.mapper.UserMapper;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.model.User;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.utility.UserValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    UserValidation userValidation;


    @Mock
    UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    AuthenticationManager authenticationManager;


    @InjectMocks
    private UserService userService;

    @Mock
    JwtService jwtService;

    private ResponseDto response;
    private User user = new User();
    private CreateUserDto createUserDto = new CreateUserDto();
    private UpdateUserDto updateUserDto = new UpdateUserDto();
    private LoginDto loginDto = new LoginDto();



    @BeforeEach
    void userSetUp() {
        ModelMapper modelMapper = new ModelMapper();

        response = new ResponseDto();
        user.setId(1L);
        user.setUsername("Breezy");
        user.setFirstName("Test");
        user.setLastName("Testing");
        user.setPassword("1234");
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(user.getCreatedAt());

        userMapper = modelMapper.map(user, UserMapper.class);
        createUserDto = modelMapper.map(user, CreateUserDto.class);
        response = modelMapper.map(user, ResponseDto.class);
        updateUserDto = modelMapper.map(user, UpdateUserDto.class);
        loginDto = modelMapper.map(user, LoginDto.class);

        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        updateUserDto = mock(UpdateUserDto.class);
        createUserDto = mock(CreateUserDto.class);
        jwtService = mock(JwtService.class);

        UserService userService1 = new UserService(
                userMapper,
                userRepository,
                bCryptPasswordEncoder,
                userValidation,
                jwtService,
                authenticationManager);

    }


    @Test
    void createUserTest() throws UserAlreadyExistsException {

        when(bCryptPasswordEncoder.encode(createUserDto.getPassword())).thenReturn("efhjdwbc");

        ResponseDto response = userService.create(createUserDto);

        verify(bCryptPasswordEncoder).encode(createUserDto.getPassword());
    }

//    @Test
//    void createUserTest_whenUserAlreadyExists(){
//        when(userRepository.findByUsername(createUserDto.getUsername())).thenReturn(user);
//        assertThrows(UserAlreadyExistsException.class, ()-> {userService.create(createUserDto);});
//    }
//


    @Test
    void authenticateUserTest() {
        AuthTokenResponseDto authTokenResponse = new AuthTokenResponseDto();
        authTokenResponse.setAccessToken("mockToken");
        userService.authenticate(loginDto);

        Authentication authentication = Mockito.mock(Authentication.class);

        assertEquals("mockToken", authTokenResponse.getAccessToken());
        Mockito.verify(authenticationManager)
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
    }



    @Test
    void updateUserTest() {
        Long id = 1L;
        UserMapper userMapper1 = mock(UserMapper.class);

        when(userValidation.ifUserExist(id)).thenReturn(user);
        when(userMapper1.responseToUserMapper(user)).thenReturn(response);

        ResponseDto responseDto = userService.update(updateUserDto, id);

        assertEquals(userMapper1.responseToUserMapper(user), response);

        verify(userValidation).ifUserExist(id);

    }

    @Test
    void getUserTest() {
        Long id = 1L;

        UserMapper userMapper1 = mock(UserMapper.class);
        when(userValidation.ifUserExist(id)).thenReturn(user);
        when(userMapper1.responseToUserMapper(user)).thenReturn(response);

        ResponseDto responseDto = userService.getUser(id);
        assertEquals(userMapper1.responseToUserMapper(user), response);

        verify(userValidation).ifUserExist(id);
    }

    @Test
    void deleteUserTest() {
        Long userId = 1L;
        Mockito.when(userValidation.ifUserExist(userId)).thenReturn(user);
        userService.deleteUser(userId);
        verify(userValidation).ifUserExist(userId);
    }

//    @Test
//    void deleteUser_WhenUSerDoesNotExists(){
//        Long userId = 22L;
//        when(userRepository.findById(userId)).thenReturn(Optional.empty());
//        assertThrows(UserNotExistException.class,()->userService.deleteUser(userId));
//        verify(userValidation).ifUserExist(userId);
//    }

    @Test
    void getAllUserTest() {

        UserMapper userMapper1 = mock(UserMapper.class);

        List<User> userList = new ArrayList<>();
        userList.add(user);

        Mockito.when(userRepository.findAll()).thenReturn(userList);

        List<ResponseDto> responseDtoList = new ArrayList<>();

        ResponseDto responseDto = new ResponseDto();
        responseDtoList.add(responseDto);

        Mockito.when(userMapper1.getAllUserMapper(userList)).thenReturn(responseDtoList);

        UserService userService1 = new UserService(
                userMapper1,
                userRepository,
                bCryptPasswordEncoder,
                userValidation,
                jwtService,
                authenticationManager);

        List<ResponseDto> result = userService1.getAll();

        assertEquals(1, result.size());

        assertEquals(responseDto, result.get(0));

        verify(userRepository).findAll();
        verify(userMapper1).getAllUserMapper(userList);
    }

    @Test
    void updatePasswordTest() {

        UserPasswordDto userPasswordDto = new UserPasswordDto();
        userPasswordDto.setUsername("Breezy");
        userPasswordDto.setOldPassword("1234");
        userPasswordDto.setNewPassword("new123");


        UserService userService1 = new UserService(
                userMapper,
                userRepository,
                bCryptPasswordEncoder,
                userValidation,
                jwtService,
                authenticationManager);


        UserPasswordDto userPasswordDto1 = new UserPasswordDto(userPasswordDto.getUsername(), userPasswordDto.getOldPassword(), userPasswordDto.getNewPassword());

        BCryptPasswordEncoder bCryptPasswordEncoder1 = new BCryptPasswordEncoder();

        String encodedPassword = bCryptPasswordEncoder1.encode(user.getPassword());
        user.setPassword(encodedPassword);
        String getUserPassword = user.getPassword();

        Mockito.when(userRepository.findByUsername(userPasswordDto.getUsername())).thenReturn(user);

        userService1.updatePassword(userPasswordDto1);


    }
}
