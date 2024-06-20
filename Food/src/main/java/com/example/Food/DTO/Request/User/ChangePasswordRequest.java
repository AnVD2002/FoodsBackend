package com.example.Food.DTO.Request.User;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordRequest {
    private String username;
    private String oldPassword;
    private String newPassword;
}
