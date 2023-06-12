package com.example.usermanagement.mapper;

import com.example.usermanagement.constants.UserConstants;
import com.example.usermanagement.dto.ResponseDto;
import com.example.usermanagement.exception.UserNotExistException;
import com.example.usermanagement.model.User;
import com.example.usermanagement.dto.UpdateUserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UserMapper {

    ModelMapper modelMapper = new ModelMapper();

    public ResponseDto responseToUserMapper(User user) {
        return modelMapper.map(user, ResponseDto.class);
    }

    public void updateMapper(UpdateUserDto updateUserDto, Optional<User> user) {
        user.ifPresent(existingUser -> {
            Optional.ofNullable(updateUserDto.getFirstName()).ifPresent(existingUser::setFirstName);
            Optional.ofNullable(updateUserDto.getLastName()).ifPresent(existingUser::setLastName);
            user.get().setUpdatedAt(LocalDateTime.now());
        });
        user.map(this::responseToUserMapper).orElseThrow(() -> new UserNotExistException(UserConstants.USERID_NOT_FOUND));
    }

    public List<ResponseDto> getAllUserMapper(List<User> userList) {
        return userList.stream().map(this::responseToUserMapper).toList();
    }

    public <T> User mapToUser(T dto) {
        return modelMapper.map(dto, User.class);
    }
}

