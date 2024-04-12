package com.example.Food.Controller;

import com.example.Food.DTO.Request.*;
import com.example.Food.DTO.Response.RegisterResponse;
import com.example.Food.Service.Foods.FoodsService;
import com.example.Food.Service.User.LoginService;
import com.example.Food.Service.User.RegisterService;
import com.example.Food.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/account/")
@RestController
public class AccountController {

    @Autowired
    private UserService userService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private RegisterService registerService;
    @CrossOrigin
    @PostMapping(path = "createAccount",produces = MediaType.APPLICATION_JSON_VALUE)
    RegisterResponse register(@RequestBody RegisterRequest request){
        return registerService.Register(request);
    }
    @CrossOrigin
    @PostMapping(path = "confirmAccount",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> ConFirmAccount(@RequestBody ConfirmRequest request){
        return registerService.ConfirmRegister(request);
    }
    @CrossOrigin
    @PostMapping(path = "login",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> Login(@RequestBody LoginRequest request ){
        return ResponseEntity.ok(loginService.Login(request));
    }





}
