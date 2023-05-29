package com.example.usermanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;
}
