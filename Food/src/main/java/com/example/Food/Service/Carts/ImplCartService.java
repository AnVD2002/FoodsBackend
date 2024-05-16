package com.example.Food.Service.Carts;

import com.example.Food.DTO.Request.CartRequest;
import org.springframework.http.ResponseEntity;

public interface ImplCartService {
    public ResponseEntity<?> addToCart(CartRequest cartRequest);
    public ResponseEntity<?> getAllCartItems(Integer userID);
    public ResponseEntity<?> removeCartItem(Integer itemID);


}
