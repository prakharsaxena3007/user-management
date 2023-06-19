package com.example.usermanagement.mapper;

import com.example.usermanagement.constants.UserConstants;
import com.example.usermanagement.dto.ResponseDTO;
import com.example.usermanagement.model.User;
import com.example.usermanagement.dto.UpdateUserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UserMapper {

    ModelMapper modelMapper = new ModelMapper();

    public ResponseDTO responseToUserMapper(User user) {
        return modelMapper.map(user, ResponseDTO.class);
    }

    public void updateMapper(UpdateUserDTO updateUserDto, Optional<User> user) {
        user.ifPresent(existingUser -> {
            Optional.ofNullable(updateUserDto.getFirstName()).ifPresent(existingUser::setFirstName);
            Optional.ofNullable(updateUserDto.getLastName()).ifPresent(existingUser::setLastName);
            user.get().setUpdatedAt(LocalDateTime.now());
        });
        user.map(this::responseToUserMapper).orElseThrow(() -> new RuntimeException(UserConstants.USER_NOT_FOUND));
    }

    public List<ResponseDTO> getAllUserMapper(List<User> userList) {
        return userList.stream().map(this::responseToUserMapper).toList();
    }

    public <T> User mapToUser(T dto) {
        return modelMapper.map(dto, User.class);
    }
}

