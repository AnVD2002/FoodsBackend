package com.example.Food.Service.User;

import com.example.Food.DTO.Request.User.ConfirmRequest;
import com.example.Food.DTO.Request.User.RegisterRequest;
import com.example.Food.DTO.Response.User.RegisterResponse;
import org.springframework.http.ResponseEntity;

public interface ImplRegisterService {

    public ResponseEntity<RegisterResponse> Register(RegisterRequest registerRequest);
    public ResponseEntity<String> sendConfirmationEmail(String confirmationCode,String recipientEmail);
    public ResponseEntity<?> ConfirmRegister(ConfirmRequest confirmRequest);
}
