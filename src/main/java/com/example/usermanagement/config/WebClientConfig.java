package com.example.usermanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ComponentScan(basePackages = {"com.example.usermanagement"})
public class WebClientConfig {
    @Bean
    public WebClient webClient(){
        return WebClient.create();
    }
}
