package com.example.Food.Service.User;

import com.example.Food.DTO.Request.LoginRequest;
import com.example.Food.DTO.Response.TokenResponse;
import com.example.Food.Entity.User.CustomUserDetails;
import com.example.Food.Entity.User.User;
import com.example.Food.Enum.RoleEnum;
import com.example.Food.JWT.JwtProvider;
import com.example.Food.Repository.DecentralizationRepository;
import com.example.Food.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
@Service
public class LoginService implements ImplLoginService{
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public UserService userService;
    @Autowired
    public DecentralizationRepository decentralizationRepository;
    @Override
    public TokenResponse Login(LoginRequest loginRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            if (userDetails.getUser().isConfirmed()) {
                String jwtToken = jwtProvider.generateToken(userDetails);
                User user = UpdateToken(loginRequest.getUsername());
                return TokenResponse.builder()
                        .expiryToken(user.getUpdatePasswordToken())
                        .accessToken(jwtToken)
                        .role(userDetails.getAuthorities().toString())
                        .message("Đăng nhập thành công")
                        .build();
            } else {
                return TokenResponse.builder().message("Tài khoản chưa được xác nhận").build();
            }
        } catch (AuthenticationException e) {
            return TokenResponse.builder().message("Sai tên tài khoản hoặc mật khẩu").build();
        } catch (Exception e) {
            return TokenResponse.builder().message("Lỗi server: " + e.getMessage()).build();
        }
    }
    public boolean isLoggedIn(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication!=null && authentication.isAuthenticated();
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
}
