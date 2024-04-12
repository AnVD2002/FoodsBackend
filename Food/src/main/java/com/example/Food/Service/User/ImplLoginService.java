package com.example.Food.Service.User;

import com.example.Food.DTO.Request.LoginRequest;
import com.example.Food.DTO.Response.TokenResponse;

public interface ImplLoginService {
    public TokenResponse Login(LoginRequest loginRequest);
}
