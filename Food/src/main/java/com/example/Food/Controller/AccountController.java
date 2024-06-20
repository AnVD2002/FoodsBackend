package com.example.Food.Controller;

import com.example.Food.DTO.Request.User.*;
import com.example.Food.DTO.Response.User.RegisterResponse;
import com.example.Food.Service.User.LoginService;
import com.example.Food.Service.User.RegisterService;
import com.example.Food.Service.User.UserCustomService;
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
    @Autowired
    private UserCustomService userCustomService;
    @CrossOrigin
    @PostMapping(path = "createAccount")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        return registerService.Register(request);
    }
    @CrossOrigin
    @PostMapping(path = "confirmAccount")
    public ResponseEntity<?> ConFirmAccount(@RequestBody ConfirmRequest request){
        return registerService.ConfirmRegister(request);
    }
    @CrossOrigin
    @PostMapping(path = "login")
    public ResponseEntity<?> Login(@RequestBody LoginRequest request ){
        return ResponseEntity.ok(loginService.Login(request));
    }
    @GetMapping("getProfile")
    public ResponseEntity<?> getProfileUser(@RequestParam String name){
        return ResponseEntity.ok(userCustomService.getUserByName(name));
    }
    @PutMapping("updateProfile")
    public ResponseEntity<?> updateProfileUser(@RequestBody UpdateProfileRequest request){
        return ResponseEntity.ok(userCustomService.updateProfileUser(request));
    }
    @PutMapping("changePassword")
    public ResponseEntity<?> changePasswordUser(@RequestBody ChangePasswordRequest request){
        return ResponseEntity.ok(userCustomService.changePassword(request));
    }


}
