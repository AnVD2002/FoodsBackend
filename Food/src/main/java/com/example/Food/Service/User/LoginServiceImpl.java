package com.example.Food.Service.User;

import com.example.Food.DTO.Request.User.ChangePasswordRequest;
import com.example.Food.DTO.Request.User.LoginRequest;
import com.example.Food.DTO.Response.User.TokenResponse;
import com.example.Food.Entity.User.CustomUserDetails;
import com.example.Food.Entity.User.User;
import com.example.Food.JWT.JwtProvider;
import com.example.Food.Repository.DecentralizationRepository;
import com.example.Food.Repository.UserRepository;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public UserServiceImpl userService;
    @Autowired
    public DecentralizationRepository decentralizationRepository;
    @Override
    public ResponseEntity<?> Login(LoginRequest loginRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            if (userDetails.getUser().isConfirmed()) {
                String refreshToken = jwtProvider.generateRefreshToken(userDetails);
                userDetails.getUser().setRefreshToken(refreshToken);
                userRepository.save(userDetails.getUser());
                String accessToken = jwtProvider.generateToken(userDetails);
                User user = UpdateToken(loginRequest.getUsername());
                return new ResponseEntity<>(TokenResponse.builder()
                        .expiryToken(user.getUpdatePasswordToken())
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .role(userDetails.getAuthorities().toString())
                        .message("Đăng nhập thành công")
                        .build(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(TokenResponse.builder().message("Tài khoản chưa được xác nhận").build(),HttpStatus.UNAUTHORIZED);
            }
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(TokenResponse.builder().message("Sai tên tài khoản hoặc mật khẩu").build(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(TokenResponse.builder().message("Lỗi server: " + e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public User UpdateToken(String username){
        Optional<User> user = userRepository.FindByName(username);
        if(user.isPresent()){
            user.get().setUpdatePasswordToken(UUID.randomUUID().toString());
            user.get().setUpdatePasswordTokenExpiry(LocalDate.from(LocalDateTime.now().plusMinutes(600000)));
            return userRepository.save(user.get());
        }
        return null;
    }
    @Override
    public ResponseEntity<?> refreshAccessToken(String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.badRequest().body("Refresh token is required");
        }
        try {
            if (jwtProvider.isTokenExpired(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token is expired");
            }

            String userName = jwtProvider.ExtractUserName(refreshToken);
            CustomUserDetails userDetails = (CustomUserDetails) userService.loadUserByUsername(userName);
            String accessToken = jwtProvider.generateToken(userDetails);
            return new ResponseEntity<>(accessToken, HttpStatus.OK);
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid refresh token");
        }
    }
    public ResponseEntity<?> changePassword(ChangePasswordRequest request) {
        Optional<User> user = userRepository.FindByName(request.getUsername());
        if(user.isEmpty()){
            return new ResponseEntity<>("not exist user", HttpStatus.NOT_FOUND);
        }
        if(request.getOldPassword().isEmpty()){
            return new ResponseEntity<>("old password is empty", HttpStatus.BAD_REQUEST);
        }
        if(request.getNewPassword().isEmpty()){
            return new ResponseEntity<>("new password is empty", HttpStatus.BAD_REQUEST);
        }
        if(!passwordEncoder.matches(request.getOldPassword(), user.get().getPassword())){
            return new ResponseEntity<>("old password does not match", HttpStatus.BAD_REQUEST);
        }
        if(!passwordEncoder.matches(request.getNewPassword(), user.get().getPassword())){
            return new ResponseEntity<>("new password does not match", HttpStatus.BAD_REQUEST);
        }
        user.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user.get());
        return new ResponseEntity<>("password changed", HttpStatus.OK);
    }









}
