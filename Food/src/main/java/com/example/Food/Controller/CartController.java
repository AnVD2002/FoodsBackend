package com.example.Food.Controller;

import com.example.Food.DTO.Request.ClientRequest.CartRequest;
import com.example.Food.DTO.Request.ClientRequest.UpdateCartItemRequest;
import com.example.Food.Service.Carts.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/cart/")
@RestController
public class CartController {
    @Autowired
    private CartServiceImpl cartService;
    @CrossOrigin
    @PostMapping(path = "addToCart",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addToCart(@RequestBody CartRequest cartRequest){
        return ResponseEntity.ok(cartService.addToCart(cartRequest));
    }
    @CrossOrigin
    @GetMapping(path = "getCartItems")
    public ResponseEntity<?> getCart(@RequestParam String userName){
        return ResponseEntity.ok(cartService.getAllCartItems(userName));
    }
    @CrossOrigin
    @DeleteMapping(path = "removeCartItem")
    public ResponseEntity<?> removeCartItem(@RequestParam Integer cartItemID){
        return ResponseEntity.ok(cartService.removeCartItem(cartItemID));
    }
    @CrossOrigin
    @PutMapping(path = "updateCartItems")
    public ResponseEntity<?> updateCartItems(@RequestBody UpdateCartItemRequest request){
        return ResponseEntity.ok(cartService.updateCartItem(request));
    }
}
