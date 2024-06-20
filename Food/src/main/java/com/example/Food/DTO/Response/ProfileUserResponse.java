package com.example.Food.DTO.Response;

import lombok.*;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileUserResponse {
    private String userName;
    private LocalDate updateAt;
    private String email;
    private String phone;
    private String img;
}
