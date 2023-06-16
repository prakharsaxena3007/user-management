package com.example.usermanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class KeycloakAdapterConfig {

    @Value("${keycloak.auth-server-url}")
    private String authServerBaseUrl;

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String keycloakResource;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String keycloakClientSecret;
}
