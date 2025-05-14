package com.example.Food.Service.User;

import com.example.Food.DTO.Request.User.ChangePasswordRequest;
import com.example.Food.DTO.Request.User.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    public ResponseEntity<?> Login(LoginRequest loginRequest);
    public ResponseEntity<?> changePassword(ChangePasswordRequest request);
    public ResponseEntity<?> refreshAccessToken(String refreshToken);
}
