package com.example.Food.Service.User;

import com.example.Food.DTO.Request.Admin.UpdateUserRequest;
import com.example.Food.DTO.Request.User.ChangePasswordRequest;
import com.example.Food.DTO.Request.User.UpdateProfileRequest;
import org.springframework.http.ResponseEntity;

public interface UserCustomServiceImpl {
    public ResponseEntity<?> getAllUser();
    public ResponseEntity<?> updateProfileUser(UpdateProfileRequest request);
    public ResponseEntity<?> changePassword(ChangePasswordRequest request);
    public ResponseEntity<?> getUserByName(String name);
    public ResponseEntity<?> createUser();
    public ResponseEntity<?> updateUser(UpdateUserRequest updateUserRequest);
    public ResponseEntity<?> deleteUser(Integer id);
    public ResponseEntity<?> getUserID(String username);
    public ResponseEntity<?> getTotalUser();
}
