package com.example.Food.Controller;

import com.example.Food.DTO.Request.ClientRequest.CancelRequest;
import com.example.Food.DTO.Request.ClientRequest.OrderRequest;
import com.example.Food.Repository.OrderDetailsRepository;
import com.example.Food.Service.Order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/order/")
@RestController
public class OrderController {
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @CrossOrigin
    @PostMapping(path = "orderFood")
    public ResponseEntity<?> orderFood(@RequestBody OrderRequest orderRequest){
        return ResponseEntity.ok(orderService.orderFoods(orderRequest));
    }
    @CrossOrigin
    @GetMapping(path = "getOrderDetail")
    private ResponseEntity<?> getOrderDetail(@RequestParam String username){
        return ResponseEntity.ok(orderService.getOrderDetails(username));
    }
    @CrossOrigin
    @PostMapping("cancelOrderDetail")
    public ResponseEntity<?> cancelOrderDetail(@RequestBody CancelRequest request){
        return ResponseEntity.ok(orderService.cancelOrder(request));
    }
    @CrossOrigin
    @GetMapping("cancelOrderDetailList")
    public ResponseEntity<?> cancelOrderDetailList(){
        return ResponseEntity.ok(orderService.getAllCancelOrderDetails());
    }



}
