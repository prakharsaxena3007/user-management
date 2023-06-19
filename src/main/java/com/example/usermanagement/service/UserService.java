package com.example.usermanagement.service;


import com.example.usermanagement.config.KeycloakAdapterConfig;
import com.example.usermanagement.dto.*;
import com.example.usermanagement.dto.ResponseDTO;
import com.example.usermanagement.exception.InvalidPasswordException;
import com.example.usermanagement.model.Response;
import com.example.usermanagement.model.User;
import com.example.usermanagement.constants.UserConstants;
import com.example.usermanagement.exception.UserAlreadyExistsException;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.utility.PasswordHelper;
import com.example.usermanagement.utility.UserValidation;
import com.example.usermanagement.exception.UserNotExistException;
import com.example.usermanagement.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final UserValidation userExisting;
    private final WebClient webClient;
    private final KeycloakAdapterConfig keycloakAdapterConfig;

    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";


    public ResponseDTO create(CreateUserDTO createUserdto) throws UserAlreadyExistsException {
        User user = userRepository.findByUsername(createUserdto.getUsername());
        if (user != null) {
            throw new UserAlreadyExistsException(String.format(UserConstants.USER_ALREADY_EXISTS, user.getUsername()));
        }
        return userMapper.responseToUserMapper(userRepository.save(userMapper.mapToUser(createUserdto)));
    }

    public String authenticate(LoginDTO loginDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", keycloakAdapterConfig.grantType);
        requestBody.add(CLIENT_ID, keycloakAdapterConfig.clientId);
        requestBody.add(CLIENT_SECRET, keycloakAdapterConfig.clientSecret);
        requestBody.add("username", loginDto.getUsername());
        requestBody.add("password", loginDto.getPassword());

        return webClient.post()
                .uri(keycloakAdapterConfig.tokenUrl)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromFormData(requestBody))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public ResponseEntity<Response> logout(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(CLIENT_ID, keycloakAdapterConfig.clientId);
        map.add(CLIENT_SECRET, keycloakAdapterConfig.clientSecret);
        map.add(request.getHeaderNames().nextElement(), request.getHeader("refresh_token"));

        return webClient.post()
                .uri(keycloakAdapterConfig.endSessionUrl)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromFormData(map))
                .retrieve()
                .toEntity(Response.class)
                .map(responseResponseEntity -> {
                    Response res = new Response();
                    if (responseResponseEntity.getStatusCode().is2xxSuccessful())
                        res.setMessage("Logged out successfully.");
                    return ResponseEntity.ok(res);
                })
                .block();
    }

    public ResponseDTO update(UpdateUserDTO updateUserDto, Long id) throws UserNotExistException {
        User user = userExisting.ifUserExist(id);
        userMapper.updateMapper(updateUserDto, Optional.of(user));
        return userMapper.responseToUserMapper(userRepository.save(user));
    }

    public ResponseDTO getUser(Long id) {
        User userExists = userExisting.ifUserExist(id);
        return userMapper.responseToUserMapper(userExists);
    }

    public void deleteUser(Long id) {
        userRepository.delete(userExisting.ifUserExist(id));
    }

    public List<ResponseDTO> getAll() throws UserNotExistException {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            throw new UserNotExistException(UserConstants.NO_USER);
        }
        return userMapper.getAllUserMapper(userList);
    }

    public void updatePassword(UserPasswordDTO userPasswordDto) throws InvalidPasswordException {
        User user = userRepository.findByUsername(userPasswordDto.getUsername());
        if (PasswordHelper.matchPassword(userPasswordDto.getOldPassword(), user.getPassword())) {
            String newEncryptedPassword = PasswordHelper.hashPassword(userPasswordDto.getNewPassword());
            user.setPassword(newEncryptedPassword);
            userRepository.save(user);
        } else {
            throw new InvalidPasswordException(UserConstants.INVALID_PASSWORD);
        }
    }
}
