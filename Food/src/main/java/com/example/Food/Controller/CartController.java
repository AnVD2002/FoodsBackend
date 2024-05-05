package com.example.Food.Controller;

import com.example.Food.DTO.Request.CartRequest;
import com.example.Food.Service.Carts.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/cart/")
@RestController
public class CartController {
    @Autowired
    private CartService cartService;
    @CrossOrigin
    @PostMapping(path = "addToCart",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addToCart(@RequestBody CartRequest cartRequest){
        return ResponseEntity.ok(cartService.addToCart(cartRequest));
    }
    @CrossOrigin
    @GetMapping(path = "getCartItems")
    public ResponseEntity<?> getCart(@RequestParam int userID){
        return ResponseEntity.ok(cartService.getAllCartItems(userID));
    }
    @CrossOrigin
    @DeleteMapping(path = "removeCartItem")
    public ResponseEntity<?> removeCartItem(@RequestParam int cartItemID){
        return ResponseEntity.ok(cartService.removeCartItem(cartItemID));
    }
}
