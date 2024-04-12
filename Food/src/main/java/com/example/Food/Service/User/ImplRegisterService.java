package com.example.Food.Service.User;

import com.example.Food.DTO.Request.ConfirmRequest;
import com.example.Food.DTO.Request.RegisterRequest;
import com.example.Food.DTO.Response.RegisterResponse;
import org.springframework.http.ResponseEntity;

public interface ImplRegisterService {

    public RegisterResponse Register(RegisterRequest registerRequest);
    public void sendConfirmationEmail(String confirmationCode,String recipientEmail);
    public ResponseEntity<?> ConfirmRegister(ConfirmRequest confirmRequest);
}
