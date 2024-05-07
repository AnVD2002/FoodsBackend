package com.example.Food.Controller;

import com.example.Food.Config.VNPayConfig;
import com.example.Food.DTO.Request.BuyRequest;
import com.example.Food.DTO.Request.TransactionalRequest;
import com.example.Food.DTO.Response.PaymentResponse;
import com.example.Food.Service.Order.OrderService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/v1/payment/")
public class PaymentController {
    @Autowired
    private OrderService orderService;
    @PostMapping("vn_pay")
    public ResponseEntity<PaymentResponse> transfer(
            HttpServletRequest req, @RequestBody TransactionalRequest transactionRequest)
    {
        return ResponseEntity.ok(orderService.apiVNPay(req,transactionRequest));
    }
    @PostMapping("/buyFoods")
    public ResponseEntity<?> pay(@RequestBody BuyRequest buyRequest){
        return ResponseEntity.ok(orderService.buyFoods(buyRequest));

    }
}
