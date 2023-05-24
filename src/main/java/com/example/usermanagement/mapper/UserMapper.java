package com.example.usermanagement.mapper;

import com.example.usermanagement.dto.Response;
import com.example.usermanagement.Model.User;
import com.example.usermanagement.dto.UpdateUserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;

@Component
public class UserMapper {

    ModelMapper modelMapper = new ModelMapper();

    public Response createMapper(User user) {
        return modelMapper.map(user, Response.class);
    }

    public Response updateMapper(UpdateUserDto updateUserDto, Optional<User> user) {
        user.ifPresent(existingUser -> {
            Optional.ofNullable(updateUserDto.getFirstName()).ifPresent(existingUser::setFirstName);
            Optional.ofNullable(updateUserDto.getLastName()).ifPresent(existingUser::setLastName);
            Optional.ofNullable(updateUserDto.getPassword()).ifPresent(existingUser::setPassword);
            Optional.ofNullable(updateUserDto.getRole()).ifPresent(existingUser::setRole);
            user.get().setUpdatedAt(updateUserDto.getUpdatedAt());
        });

        return modelMapper.map(user, Response.class);
    }

    public Response getUserMapper(Optional<User> user) {
        Response response = null;
        if (user != null) {
            response = modelMapper.map(user, Response.class);
        }
        return response;
    }

    public void deleteUserMapper(Optional<User> userOptional) {
        modelMapper.map(userOptional.get(), Response.class);
    }

    public List<Response> getAllUserMapper(List<User> userList) {
        return userList.stream().map(user -> modelMapper.map(user, Response.class)).toList();
    }

    public <T> User mapToUser(T dto) {
        return modelMapper.map(dto, User.class);
    }
}
