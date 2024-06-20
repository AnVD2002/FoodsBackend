package com.example.Food.Controller;

import com.example.Food.DTO.Request.ClientRequest.OrderRequest;
import com.example.Food.Service.Order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/order/")
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @CrossOrigin
    @PostMapping(path = "orderFood")
    public ResponseEntity<?> orderFood(@RequestBody OrderRequest orderRequest){
        return ResponseEntity.ok(orderService.orderFoods(orderRequest));

    }
}
