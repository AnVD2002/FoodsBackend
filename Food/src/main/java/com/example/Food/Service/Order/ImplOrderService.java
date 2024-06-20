package com.example.Food.Service.Order;

import com.example.Food.DTO.Request.ClientRequest.OrderRequest;
import org.springframework.http.ResponseEntity;

public interface ImplOrderService {
    public ResponseEntity<?> orderFoods(OrderRequest orderRequest);
}
