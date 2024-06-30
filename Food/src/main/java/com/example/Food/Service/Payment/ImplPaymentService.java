package com.example.Food.Service.Payment;

import com.example.Food.DTO.Request.ClientRequest.TransactionalRequest;
import com.example.Food.DTO.Response.VnPay_Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface ImplPaymentService {
    public VnPay_Response apiVNPay(HttpServletRequest request, TransactionalRequest transactionRequest);
    public ResponseEntity<?> getToTalInThisMonth();
    public ResponseEntity<?> getQuantityThisMonth();
}
