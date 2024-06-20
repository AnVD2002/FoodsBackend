package com.example.Food.DTO.Request.User;

import com.example.Food.DTO.Response.User.RegisterResponse;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmRequest {
    RegisterResponse registerResponse;
    String code;
}
