package com.example.usermanagement.mapper;

import com.example.usermanagement.dto.ResponseDto;
import com.example.usermanagement.dto.UpdateUserDto;
import com.example.usermanagement.exception.UserNotExistException;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;
    private ResponseDto userResponseDto;
    private User user = new User();
    private UpdateUserDto updateUserDto = new UpdateUserDto();

    @BeforeEach
    void setUp() {
        ModelMapper modelMapper = new ModelMapper();

        user.setId(1L);
        user.setUsername("Breezy");
        user.setFirstName("Test");
        user.setLastName("Testing");
        user.setPassword("1234");
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(user.getCreatedAt());

        userResponseDto = modelMapper.map(user, ResponseDto.class);
        updateUserDto = modelMapper.map(user, UpdateUserDto.class);
    }


    @Test
    void responseToUserMapperTest() {
        ResponseDto responseDto = userMapper.responseToUserMapper(user);
        assertNotNull(responseDto);
        assertEquals(1L, responseDto.getId());
        assertEquals("Test", responseDto.getFirstName());
        assertEquals("Testing", responseDto.getLastName());
    }

    @Test
    void updateUserMapperTest() {

        User userMocked = mock(User.class);

        Optional<User> user1 = Optional.of(userMocked);
        userMapper.updateMapper(updateUserDto, user1);

        verify(userMocked).setFirstName("Test");
        verify(userMocked).setLastName("Testing");
        verify(userMocked).setUpdatedAt(any(LocalDateTime.class));
        assertDoesNotThrow(() -> userMapper.updateMapper(updateUserDto, user1));

    }

    @Test
    void updateUserMapperTest_WhenUserNotExists(){

        updateUserDto.setFirstName("Test");
        updateUserDto.setLastName("Testing");
        Optional<User> user1 = Optional.empty();
        assertThrows(UserNotExistException.class,()->{userMapper.updateMapper(updateUserDto,user1);
        });
    }

    @Test
    void getAllUserTest() {
        List<User> userList = new ArrayList<>();
        userList.add(user);

        UserMapper userMapper1 = new UserMapper();

        List<ResponseDto> responseDtoList = userMapper1.getAllUserMapper(userList);

        assertNotNull(responseDtoList);
        assertEquals(userList.size(), responseDtoList.size());
        assertTrue(responseDtoList.stream().allMatch(Objects::nonNull));
        for (int i = 0; i < userList.size(); i++) {
            User user1 = userList.get(i);
            ResponseDto responseDto = responseDtoList.get(i);

            assertEquals(user1.getFirstName(), responseDto.getFirstName());
            assertEquals(user1.getLastName(), responseDto.getLastName());
        }
    }

    @Test
    void getAllUser_WhenNoUserExists(){
        List<User> userList=new ArrayList<>();
        List<ResponseDto> responseDtoList = userMapper.getAllUserMapper(userList);
        assertEquals(0,responseDtoList.size());

    }

    @Test
    void testMapToUser() {
        User user = userMapper.mapToUser(userResponseDto);
        assertNotNull(user);
    }
}