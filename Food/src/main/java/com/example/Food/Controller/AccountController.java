package com.example.Food.Controller;

import com.example.Food.DTO.Request.User.*;
import com.example.Food.Service.User.LoginServiceImpl;
import com.example.Food.Service.User.RegisterServiceImpl;
import com.example.Food.Service.User.UserCustomServiceImpl;
import com.example.Food.Service.User.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/account/")
@RestController
public class AccountController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private LoginServiceImpl loginService;
    @Autowired
    private RegisterServiceImpl registerService;
    @Autowired
    private UserCustomServiceImpl userCustomService;
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
    @CrossOrigin
    @PostMapping(path = "refreshToken")
    public ResponseEntity<?> RefreshToken(@RequestParam String refreshToken){
        return ResponseEntity.ok(loginService.refreshAccessToken(refreshToken));
    }
    @CrossOrigin
    @GetMapping("getProfile")
    public ResponseEntity<?> getProfileUser(@RequestParam String name){
        return ResponseEntity.ok(userCustomService.getUserByName(name));
    }
    @CrossOrigin
    @PutMapping("updateProfile")
    public ResponseEntity<?> updateProfileUser(@RequestBody UpdateProfileRequest request){
        return ResponseEntity.ok(userCustomService.updateProfileUser(request));
    }
    @CrossOrigin
    @PutMapping("changePassword")
    public ResponseEntity<?> changePasswordUser(@RequestBody ChangePasswordRequest request){
        return ResponseEntity.ok(userCustomService.changePassword(request));
    }
    @CrossOrigin
    @GetMapping("getUserID")
    public ResponseEntity<?> getUserID(@RequestParam String username){
        return ResponseEntity.ok(userCustomService.getUserID(username));
    }


}
