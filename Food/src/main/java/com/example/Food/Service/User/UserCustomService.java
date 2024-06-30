package com.example.Food.Service.User;
import com.example.Food.DTO.Request.Admin.UpdateUserRequest;
import com.example.Food.DTO.Request.User.ChangePasswordRequest;
import com.example.Food.DTO.Request.User.UpdateProfileRequest;
import com.example.Food.DTO.Response.Admin.ViewUserResponse;
import com.example.Food.DTO.Response.ProfileUserResponse;
import com.example.Food.DTO.Response.UpdateProfileResponse;
import com.example.Food.Entity.Cart.CartItems;
import com.example.Food.Entity.Cart.Carts;
import com.example.Food.Entity.Order.Orders;
import com.example.Food.Entity.User.User;
import com.example.Food.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserCustomService implements UserCustomServiceImpl{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private OrdersRepository ordersRepository;


    @Override
    public ResponseEntity<?> getAllUser() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        List<ViewUserResponse> viewUserResponses = users.stream()
                .map(user -> new ViewUserResponse(
                        user.getUserID(),
                        user.getUserName(),
                        user.getDecentralizationID(),
                        user.isConfirmed(),
                        user.getEmail(),
                        user.getNumberPhone(),
                        user.getImg()))
                .toList();
        return ResponseEntity.ok(viewUserResponses);

    }
    public ResponseEntity<?> getUserByName(String name) {
        if(name.isEmpty()){
            return new ResponseEntity<>("Name cannot be empty", HttpStatus.BAD_REQUEST);
        }
        Optional<User> userOpt = userRepository.FindByName(name);
        if(userOpt.isEmpty()){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        ProfileUserResponse profileUserResponse ;
        profileUserResponse = ProfileUserResponse.builder()
                .phone(userOpt.get().getNumberPhone())
                .email(userOpt.get().getEmail())
                .img(userOpt.get().getImg())
                .userName(userOpt.get().getUserName())
                .updateAt(userOpt.get().getUpdateDate())
                .build();

        return new ResponseEntity<>(profileUserResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createUser() {
        return null;
    }

    @Override
    public ResponseEntity<?> updateUser(UpdateUserRequest updateUserRequest) {
        if(updateUserRequest.getUserID()==null){
            return new ResponseEntity<>("UserID cannot be null", HttpStatus.BAD_REQUEST);
        }

        Optional<User> userOpt = userRepository.findById(updateUserRequest.getUserID());
        if(userOpt.isEmpty()){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        if(updateUserRequest.getNumberPhone()==null){
            return new ResponseEntity<>("không được để trống số điện thoại", HttpStatus.NOT_ACCEPTABLE);
        }
        else {
            userOpt.get().setNumberPhone(updateUserRequest.getNumberPhone());
        }
        if(updateUserRequest.getUserName().isEmpty()){
            userOpt.get().setUserName(userOpt.get().getUserName());
        }
        else {
            userOpt.get().setUserName(updateUserRequest.getUserName());
        }
        if (updateUserRequest.getImg().isEmpty()){
            userOpt.get().setImg("");
        }
        else {
            userOpt.get().setImg(updateUserRequest.getImg());
        }
        if(updateUserRequest.getMail().isEmpty()){
            userOpt.get().setEmail(userOpt.get().getEmail());
        }
        else {
            userOpt.get().setEmail(updateUserRequest.getMail());
        }

        userOpt.get().setUpdateDate(LocalDate.now());
        userOpt.get().setConfirmed(updateUserRequest.isStatus());
        userRepository.save(userOpt.get());

        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> deleteUser(Integer id) {
        if (id == null) {
            return new ResponseEntity<>("UserID cannot be null", HttpStatus.BAD_REQUEST);
        }

        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User user = userOpt.get();
        Carts cart = user.getCart();
        List<Orders> orders = user.getOrders();

        if (cart != null) {
            List<CartItems> cartItems = cart.getCartItems();
            if (cartItems != null && !cartItems.isEmpty()) {
                cartItemsRepository.deleteAll(cartItems);
            }
            cartRepository.delete(cart);
        }
        if (!orders.isEmpty()) {
            ordersRepository.deleteAll(orders);
        }
        userRepository.delete(user);

        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }



    @Override
    public ResponseEntity<?> updateProfileUser(UpdateProfileRequest request) {
        UpdateProfileResponse response ;
        if(request.getUsername().isEmpty()){
            response = UpdateProfileResponse.builder().message("Vui lòng điền username").build();
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
        Optional<User> user = userRepository.FindByName(request.getUsername());
        if(user.isEmpty()){
            response= UpdateProfileResponse.builder().message("not exist user").build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        if(request.getNewUserName().isEmpty()){
            user.get().setUserName(user.get().getUserName());
        }
        else {
            user.get().setUserName(request.getNewUserName());
        }
        if(request.getPhone().isEmpty()){
            user.get().setNumberPhone(user.get().getNumberPhone());
        }
        else {
            user.get().setNumberPhone(request.getPhone());
        }
        if(request.getImg().isEmpty()){
            user.get().setImg(user.get().getImg());
        }
        else {
            user.get().setImg(request.getImg());
        }
        user.get().setUpdateDate(LocalDate.now());
        userRepository.save(user.get());

        User userBuilder = User.builder()
                .UserID(user.get().getUserID())
                .userName(user.get().getUserName())
                .Email(user.get().getEmail())
                .numberPhone(user.get().getNumberPhone())
                .img(user.get().getImg())
                .build();
        return new ResponseEntity<>(userBuilder,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> changePassword(ChangePasswordRequest request) {
        Optional<User> user = userRepository.FindByName(request.getUsername());
        if(user.isEmpty()){
            return new ResponseEntity<>("Not have user",HttpStatus.NO_CONTENT);
        }
        if(request.getOldPassword().isEmpty()){
            return new ResponseEntity<>("Old password is empty",HttpStatus.NO_CONTENT);
        }
        if(request.getNewPassword().isEmpty()){
            return new ResponseEntity<>("New password is empty",HttpStatus.NO_CONTENT);
        }
        if(!passwordEncoder.matches(request.getOldPassword(),user.get().getPassword())){
            return new ResponseEntity<>("Old password is not correct",HttpStatus.UNAUTHORIZED);
        }
        user.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user.get());
        return new ResponseEntity<>("update Completed",HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> getUserID(String username){
        if(username.isEmpty()){
            return new ResponseEntity<>("Username is empty",HttpStatus.BAD_REQUEST);
        }
        Optional<User> user = userRepository.FindByName(username);
        if(user.isEmpty()){
            return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
        }
        Integer id = user.get().getUserID();
        return new ResponseEntity<>(id,HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> getTotalUser(){
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            return new ResponseEntity<>("not exist user",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users.size(), HttpStatus.OK);
    }
}
