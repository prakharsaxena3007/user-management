package com.example.usermanagement.mapper;

import com.example.usermanagement.dto.AuthenticationResponse;
import com.example.usermanagement.constants.UserConstants;
import com.example.usermanagement.dto.Response;
import com.example.usermanagement.model.User;
import com.example.usermanagement.dto.UpdateUserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class UserMapper {

    ModelMapper modelMapper = new ModelMapper();


    public AuthenticationResponse createUserMapper(User user) {
        return modelMapper.map(user, AuthenticationResponse.class);
    }
    public Response responseToUserMapper(User user) {
        return modelMapper.map(user, Response.class);
    }

    public Response updateMapper(UpdateUserDto updateUserDto, Optional<User> user) {
        user.ifPresent(existingUser -> {
            Optional.ofNullable(updateUserDto.getFirstName()).ifPresent(existingUser::setFirstName);
            Optional.ofNullable(updateUserDto.getLastName()).ifPresent(existingUser::setLastName);
            user.get().setUpdatedAt(updateUserDto.getUpdatedAt());
        });
        return user.map(this::responseToUserMapper).orElseThrow(() -> new RuntimeException(UserConstants.USER_NOT_FOUND));
    }

    public List<Response> getAllUserMapper(List<User> userList) {
        return userList.stream().map(this::responseToUserMapper).toList();
    }

    public <T> User mapToUser(T dto) {
        return modelMapper.map(dto, User.class);
    }
}

