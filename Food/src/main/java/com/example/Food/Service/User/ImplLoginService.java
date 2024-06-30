package com.example.Food.Service.User;

import com.example.Food.DTO.Request.User.ChangePasswordRequest;
import com.example.Food.DTO.Request.User.LoginRequest;
import com.example.Food.DTO.Response.User.TokenResponse;
import org.springframework.http.ResponseEntity;

public interface ImplLoginService {
    public ResponseEntity<?> Login(LoginRequest loginRequest);
    public ResponseEntity<?> changePassword(ChangePasswordRequest request);
    public ResponseEntity<?> refreshAccessToken(String refreshToken);
}
