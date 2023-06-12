package com.example.usermanagement.utility;

import com.example.usermanagement.constants.UserConstants;
import com.example.usermanagement.exception.UserNotExistException;
import com.example.usermanagement.model.User;
import com.example.usermanagement.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserValidationTest {

    @Mock
    private UserRepository userRepository;
    private UserValidation userValidation;

    @BeforeEach
    void setUp(){
        userValidation=new UserValidation(userRepository);
    }


    @Test
    void ifUserExistsTest_WhenUserExists(){
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);
        User response = userValidation.ifUserExist(userId);
        Assertions.assertNotNull(user);

        Assertions.assertEquals(userId,user.getId());

    }

    @Test
    void ifUserExistsTest_WhenUserDoesNotExists(){
        Long userId = 12L;
        List<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);
        UserNotExistException exception= assertThrows(UserNotExistException.class,()-> userValidation.ifUserExist(userId));
        Assertions.assertEquals(String.format(UserConstants.USERID_NOT_FOUND,userId), exception.getMessage());
    }
}
