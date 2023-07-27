package com.example.usermanagement.config;

import com.example.usermanagement.config.opa.OPAAuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity
@ComponentScan(basePackages = {"com.example.usermanagement"})
public class SecurityConfig {

    @Autowired
    private OPAAuthorizationManager opaAuthorizationManager;

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/api/v1/login","/api/v1/users/all-users","/api/v1/register")
                                .permitAll()
                                .requestMatchers("api/v1/users/*")
                                .access(opaAuthorizationManager)
                                .anyRequest()
                                .authenticated());
        http
                .oauth2Login();
        http
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        return http.build();
    }
}
