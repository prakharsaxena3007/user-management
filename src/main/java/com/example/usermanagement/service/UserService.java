package com.example.usermanagement.service;


import com.example.usermanagement.dto.*;
import com.example.usermanagement.dto.ResponseDto;
import com.example.usermanagement.exception.InvalidPasswordException;
import com.example.usermanagement.model.IntrospectResponse;
import com.example.usermanagement.model.Response;
import com.example.usermanagement.model.User;
import com.example.usermanagement.constants.UserConstants;
import com.example.usermanagement.exception.UserAlreadyExistsException;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.utility.PasswordHelper;
import com.example.usermanagement.utility.UserValidation;
import com.example.usermanagement.exception.UserNotExistException;
import com.example.usermanagement.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final UserValidation userExisting;
    private final WebClient webClient;

    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String issueUrl;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.keycloak.authorization-grant-type}")
    private String grantType;


    public UserService(UserMapper userMapper, UserRepository userRepository, UserValidation userExisting, WebClient webClient) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.userExisting = userExisting;
        this.webClient = webClient;
    }

    public ResponseDto create(CreateUserDto createUserdto) throws UserAlreadyExistsException {
        User user = userRepository.findByUsername(createUserdto.getUsername());
        if (user != null) {
            throw new UserAlreadyExistsException(String.format(UserConstants.USER_ALREADY_EXISTS, user.getUsername()));
        }
        return userMapper.responseToUserMapper(userRepository.save(userMapper.mapToUser(createUserdto)));
    }

    public String authenticate(LoginDto loginDto) {

        String keycloakUrl = "http://localhost:8080/realms/usermanagement/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", grantType);
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("username", loginDto.getUsername());
        requestBody.add("password", loginDto.getPassword());

        WebClient webClient1 = WebClient.builder().build();

        return webClient1.post()
                .uri(keycloakUrl)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromFormData(requestBody))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public ResponseEntity<Response> logout (TokenRequestDto tokenRequest){

        String endSessionUrl = "http://localhost:8080/realms/usermanagement/protocol/openid-connect/logout";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("refresh_token", tokenRequest.getToken());

        WebClient webClient1 = WebClient.builder().build();


      return webClient1.post()
                .uri(endSessionUrl)
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

    public ResponseEntity<IntrospectResponse> introspect(TokenRequestDto request) {

        String introspectionUrl = "http://localhost:8080/realms/usermanagement/protocol/openid-connect/token/introspect";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("token", request.getToken());



        return webClient.post()
                .uri(introspectionUrl)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromFormData(map))
                .retrieve()
                .bodyToMono(IntrospectResponse.class)
                .map(responseEntity -> ResponseEntity.ok().body(responseEntity))
                .block();
    }


    public ResponseDto update(UpdateUserDto updateUserDto, Long id) throws UserNotExistException {
        User user = userExisting.ifUserExist(id);
        userMapper.updateMapper(updateUserDto, Optional.of(user));
        return userMapper.responseToUserMapper(userRepository.save(user));
    }

    public ResponseDto getUser(Long id) {
        User userExists = userExisting.ifUserExist(id);
        return userMapper.responseToUserMapper(userExists);
    }

    public void deleteUser(Long id) {
        userRepository.delete(userExisting.ifUserExist(id));
    }

    public List<ResponseDto> getAll() throws UserNotExistException {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            throw new UserNotExistException(UserConstants.NO_USER);
        }
        return userMapper.getAllUserMapper(userList);
    }

    public void updatePassword(UserPasswordDto userPasswordDto) throws InvalidPasswordException {
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
