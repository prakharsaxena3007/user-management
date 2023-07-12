package com.example.usermanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.example.usermanagement"})
public class KeycloakAdapterConfig {

    @Value("${keycloak.auth-server-url}")
    public String authServerBaseUrl;

    @Value("${keycloak.realm}")
    public String keycloakRealm;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    public String keycloakResource;

    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    public String issueUrl;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    public String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    public String clientSecret;

    @Value("${spring.security.oauth2.client.registration.keycloak.authorization-grant-type}")
    public String grantType;

    @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
    public String tokenUrl;

    @Value("${keycloak.end-session-uri}")
    public String endSessionUrl;

}
