package com.example.Food.Service.User;

import com.example.Food.DTO.Request.User.ConfirmRequest;
import com.example.Food.DTO.Request.User.RegisterRequest;
import com.example.Food.DTO.Response.User.RegisterResponse;
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
import org.springframework.mail.MailException;


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
    public ResponseEntity<RegisterResponse> Register(RegisterRequest registerRequest) {
        Optional<User> userCheck = userRepository.FindByName(registerRequest.getUsername());
        Optional<Decentralization> decentralization = decentralizationRepository.findByAuthorityName(RoleEnum.USER.toString());
        if(decentralization.isEmpty()){
            return new ResponseEntity<>(RegisterResponse.builder().message("not exist").build(),HttpStatus.NOT_FOUND);
        }
        OTP otp = new OTP();
        String newOTP = otp.GenerateOTP();

        if(userCheck.isPresent()){
            if(userCheck.get().isConfirmed()){
                ResponseEntity<String> message= sendConfirmationEmail(newOTP, registerRequest.getEmail());
                if(!message.getStatusCode().equals(HttpStatus.OK)){
                    return new ResponseEntity<>(RegisterResponse.builder().message(message.getBody()).build(),HttpStatus.OK);
                }
                userCheck.get().setPassword(passwordEncoder.encode(registerRequest.getPassword()));
                userCheck.get().setEmail(registerRequest.getEmail());
                userRepository.save(userCheck.get());
                return new ResponseEntity<>(RegisterResponse.builder().message("Đã tồn tại tài khoản").build(),HttpStatus.NOT_FOUND);
            }
            sendConfirmationEmail(newOTP, registerRequest.getEmail());
            return new ResponseEntity<>(RegisterResponse.builder()
                    .username(userCheck.get().getUserName())
                    .email(registerRequest.getEmail())
                    .code(newOTP)
                    .message("Còn 1 bước nữa để thành công")
                    .build(), HttpStatus.OK);
        }
        ResponseEntity<String> message= sendConfirmationEmail(newOTP, registerRequest.getEmail());
        if(!message.getStatusCode().equals(HttpStatus.OK)){
            return new ResponseEntity<>(RegisterResponse.builder().message(message.getBody()).build(),HttpStatus.OK);
        }
        User user = new User();
        user.setDecentralization(decentralization.get());
        user.setConfirmed(false);
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUserName(registerRequest.getUsername());
        userRepository.save(user);

        return new ResponseEntity<>(RegisterResponse.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .code(newOTP)
                .message("Còn 1 bước nữa để thành công")
                .build(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<String> sendConfirmationEmail(String confirmationCode, String recipientEmail) {
        if (recipientEmail == null || recipientEmail.isEmpty()) {
            return new ResponseEntity<>("Địa chỉ email không được để trống.", HttpStatus.BAD_REQUEST);
        }
        if (!isValidEmail(recipientEmail)) {
            return new ResponseEntity<>("Địa chỉ email không hợp lệ.", HttpStatus.BAD_REQUEST);
        }

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject("Xác nhận đăng ký tài khoản");
        mailMessage.setText("Mã xác nhận của bạn là: " + confirmationCode);

        try {
            javaMailSender.send(mailMessage);
            return new ResponseEntity<>("Email xác nhận đã được gửi thành công.", HttpStatus.OK);
        } catch (MailException e) {
            return new ResponseEntity<>("Lỗi khi gửi email: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }



}
