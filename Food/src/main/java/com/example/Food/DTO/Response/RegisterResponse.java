package com.example.Food.DTO.Response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    private String username;

    private String email;

    private String phoneNumber;

    private boolean status;

    private String code;

    private String message;
}
