package com.example.Food.Service.Order;

import com.example.Food.DTO.Request.BuyRequest;
import com.example.Food.DTO.Request.TransactionalRequest;
import com.example.Food.DTO.Response.PaymentResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface ImplOrderService {
    public PaymentResponse apiVNPay(HttpServletRequest request, TransactionalRequest transactionalRequest);
    public ResponseEntity<?> buyFoods(BuyRequest buyRequest);
}
