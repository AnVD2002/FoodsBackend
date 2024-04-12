package com.example.Food.DTO.Request;

import com.example.Food.DTO.Response.RegisterResponse;
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
