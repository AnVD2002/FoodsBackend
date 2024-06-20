package com.example.Food.Controller;

import com.example.Food.DTO.Request.ClientRequest.PaymentRequest;
import com.example.Food.DTO.Request.ClientRequest.TransactionalRequest;
import com.example.Food.DTO.Response.VnPay_Response;
import com.example.Food.Service.Order.OrderService;
import com.example.Food.Service.Payment.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment/")
public class PaymentController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PaymentService paymentService;
    @PostMapping("vn_pay")
    public ResponseEntity<VnPay_Response> transfer(
            HttpServletRequest req, @RequestBody TransactionalRequest transactionRequest)
    {
        return ResponseEntity.ok(paymentService.apiVNPay(req,transactionRequest));
    }
    @PostMapping("pay_normal")
    public ResponseEntity<?> payNormal(@RequestBody PaymentRequest request){
        return ResponseEntity.ok(paymentService.payment(request));

    }
}
