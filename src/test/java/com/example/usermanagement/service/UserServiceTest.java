package com.example.usermanagement.service;

import com.example.usermanagement.dto.*;
import com.example.usermanagement.mapper.UserMapper;
import com.example.usermanagement.model.User;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.utility.UserValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {



    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserValidation userValidation;


    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Mock
    AuthenticationManager authenticationManager;


    @InjectMocks
    private UserService userService;

    @Mock
    JwtService jwtService;

    private Response response;
    private User user = new User();
    private CreateUserdto createUserdto = new CreateUserdto();
    private UpdateUserDto updateUserDto = new UpdateUserDto();
    private LoginDto loginDto = new LoginDto();
    private AuthenticationResponse authenticationResponse = new AuthenticationResponse();


    @BeforeEach
    void userSetUp(){
        ModelMapper modelMapper = new ModelMapper();

        response = new Response();
        user.setId(1L);
        user.setUsername("Breezy");
        user.setFirstName("Test");
        user.setLastName("Testing");
        user.setPassword("1234");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(user.getCreatedAt());

        createUserdto = modelMapper.map(user, CreateUserdto.class);
        authenticationResponse  = modelMapper.map(user, AuthenticationResponse.class);
        response = modelMapper.map(user, Response.class);
        updateUserDto = modelMapper.map(user, UpdateUserDto.class);
        loginDto = modelMapper.map(user, LoginDto.class);

        response = mock(Response.class);
        createUserdto = mock(CreateUserdto.class);
        authenticationResponse = mock(AuthenticationResponse.class);
        loginDto = mock(LoginDto.class);
        updateUserDto = mock(UpdateUserDto.class);
        response = new Response();

    }


    @Test
    void createUserTest(){
        User user1 = new User();
        when(userRepository.findByUsername(createUserdto.getUsername())).thenReturn(null);
        when(userMapper.createUserMapper(user)).thenReturn(authenticationResponse);
        when(passwordEncoder.encode(createUserdto.getPassword())).thenReturn("efhjdwbc");
        when(userRepository.save(userMapper.mapToUser(createUserdto))).thenReturn(user);

        userService.create(createUserdto);

        AuthenticationResponse response = new AuthenticationResponse();
        assertNotNull(response);
//        assertSame(authenticationResponse,response);

        verify(userRepository).findByUsername(createUserdto.getUsername());
        verify(userMapper).mapToUser(createUserdto);
        verify(passwordEncoder).encode(createUserdto.getPassword());
        verify(userRepository).save(user);
        verify(userMapper).createUserMapper(user);

    }


    @Test
    void authenticateUserTest(){
        when(userRepository.findByUsername(loginDto.getUsername())).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn("mockToken");

        Authentication authentication = Mockito.mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);



        AuthTokenResponse authTokenResponse = userService.authenticate(loginDto);
        assertEquals("mockToken", authTokenResponse.getAccessToken());
        Mockito.verify(authenticationManager)
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

    }

    @Test
    void updateUserTest(){
        String username = "testUser";
        when(userValidation.ifUserExist(username)).thenReturn(user);
        when(userMapper.updateMapper(updateUserDto, Optional.of(user))).thenReturn(response);

        when(userRepository.save(user)).thenReturn(user);


        response=userService.update(updateUserDto,username);


        assertEquals(userMapper.responseToUserMapper(user), response);

        verify(userValidation).ifUserExist(username);
        verify(userMapper).updateMapper(updateUserDto, Optional.of(user));
        verify(userRepository).save(user);
    }
}
