package com.example.usermanagement.mapper;

import com.example.usermanagement.Model.Response;
import com.example.usermanagement.Model.User;
import com.example.usermanagement.dto.CreateUserdto;
import com.example.usermanagement.dto.UpdateUserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UserMapper {

    ModelMapper modelMapper = new ModelMapper();

    public Response createMapper(CreateUserdto createUserdto, List<User> userList) {
        User user = modelMapper.map(createUserdto, User.class);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userList.add(user);
        return modelMapper.map(user, Response.class);
    }

    public Response updateMapper(UpdateUserDto updateUserDto, List<User> userList, String username) {
        Response response = new Response();
        for (User user : userList) {
            if (user.getUserName().equals(username)) {
                Optional<User> userOptional = Optional.of(user);
                Optional.ofNullable(updateUserDto.getFirstName()).ifPresent(user::setFirstName);
                Optional.ofNullable(updateUserDto.getLastName()).ifPresent(user::setLastName);
                Optional.ofNullable(updateUserDto.getPassword()).ifPresent(user::setPassword);
                Optional.ofNullable(updateUserDto.getRole()).ifPresent(user::setRole);
                user.setUpdatedAt(LocalDateTime.now());
                response = modelMapper.map(user, Response.class);
            }
        }
        return response;
    }

    public Response getUserMapper(String username, List<User> userList) {
        Response response = new Response();
        for (User user : userList) {
            if (user.getUserName().equals(username)) {
                response = modelMapper.map(user, Response.class);
            }
        }
        return response;
    }

    public Response deleteUserMapper(String username, List<User> userList) {
        Response response = new Response();
        userList.removeIf(user -> user.getUserName().equals(username));
        return response;
    }

    public List<Response> getAllUserMapper(List<User> userList) {
        return userList.stream().map(user -> modelMapper.map(user, Response.class)).toList();
    }
}
