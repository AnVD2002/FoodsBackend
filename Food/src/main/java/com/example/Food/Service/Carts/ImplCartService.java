package com.example.Food.Service.Carts;

import com.example.Food.DTO.Request.ClientRequest.CartRequest;
import com.example.Food.DTO.Request.ClientRequest.UpdateCartItemRequest;
import org.springframework.http.ResponseEntity;

public interface ImplCartService {
    public ResponseEntity<?> addToCart(CartRequest cartRequest);
    public ResponseEntity<?> getAllCartItems(String userName);
    public ResponseEntity<?> removeCartItem(Integer itemID);
    public ResponseEntity<?> updateCartItem(UpdateCartItemRequest request);
}
