package com.example.Food.Service.User;

import com.example.Food.DTO.Request.ConfirmRequest;
import com.example.Food.DTO.Request.RegisterRequest;
import com.example.Food.DTO.Response.RegisterResponse;
import com.example.Food.Entity.User.Decentralization;
import com.example.Food.Entity.User.User;
import com.example.Food.Enum.RoleEnum;
import com.example.Food.Repository.DecentralizationRepository;
import com.example.Food.Repository.UserRepository;
import com.example.Food.Untils.OTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class RegisterService implements ImplRegisterService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    public JavaMailSender javaMailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DecentralizationRepository decentralizationRepository;
    @Override
    public ResponseEntity<?> ConfirmRegister(ConfirmRequest confirmRequest) {
        Optional<User> userCheck = userRepository.FindByName(confirmRequest.getRegisterResponse().getUsername());
        if(userCheck.isPresent()){
            if(confirmRequest.getCode().equals(confirmRequest.getRegisterResponse().getCode())){
                Optional<Decentralization> decentralization = decentralizationRepository.findByAuthorityName(RoleEnum.USER.toString());
                if (decentralization.isEmpty()) {
                    return new ResponseEntity<>("Khong ton tai ", HttpStatus.NOT_FOUND);
                }
                userCheck.get().setConfirmed(true);
                userRepository.save(userCheck.get());
                return new ResponseEntity<>("Tao tk thanh cong ", HttpStatus.OK);
            }
            return new ResponseEntity<>("Sai code", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Ban chua tao tk", HttpStatus.NOT_FOUND);
    }

    @Override
    @Transactional
    public RegisterResponse Register(RegisterRequest registerRequest) {
        Optional<User> userCheck = userRepository.FindByName(registerRequest.getUsername());
        Optional<Decentralization> decentralization = decentralizationRepository.findByAuthorityName(RoleEnum.USER.toString());
        OTP otp = new OTP();
        String newOTP = otp.GenerateOTP();
        User user = new User();
        if(userCheck.isPresent()){
            if(userCheck.get().isConfirmed()){
                userCheck.get().setPassword(passwordEncoder.encode(registerRequest.getPassword()));
                userCheck.get().setEmail(registerRequest.getEmail());
                userRepository.save(userCheck.get());
                return RegisterResponse.builder().message("Đã tồn tại tài khoản").build();
            }
            sendConfirmationEmail(newOTP, registerRequest.getEmail());
            return RegisterResponse.builder()
                    .username(userCheck.get().getUserName())
                    .email(registerRequest.getEmail())
                    .code(newOTP)
                    .message("Còn 1 bước nữa để thành công")
                    .build();
        }
        user.setDecentralization(decentralization.get());
        user.setConfirmed(false);
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUserName(registerRequest.getUsername());
        userRepository.save(user);
        sendConfirmationEmail(newOTP, registerRequest.getEmail());
        return RegisterResponse.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .code(newOTP)
                .message("Còn 1 bước nữa để thành công")
                .build();
    }

    @Override
    @Transactional
    public void sendConfirmationEmail(String confirmationCode,String recipientEmail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject("Xác nhận đăng ký tài khoản");
        mailMessage.setText("Mã xác nhận của bạn là: " + confirmationCode);
        javaMailSender.send(mailMessage);
    }
}
