package com.example.Food.DTO.Response.User;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponse {

    private String accessToken;

    private String refreshToken;

    private String expiryToken;


    private String role;

    private String message;
}
