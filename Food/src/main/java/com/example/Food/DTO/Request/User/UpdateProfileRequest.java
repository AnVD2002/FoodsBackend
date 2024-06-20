package com.example.Food.DTO.Request.User;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProfileRequest {
    private String username;
    private String newUserName;
    private String phone;
    private String img;
}
