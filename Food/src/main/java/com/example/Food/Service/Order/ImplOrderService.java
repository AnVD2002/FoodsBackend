package com.example.Food.Service.Order;

import com.example.Food.DTO.Request.ClientRequest.CancelRequest;
import com.example.Food.DTO.Request.ClientRequest.ConfirmPaymentRequest;
import com.example.Food.DTO.Request.ClientRequest.OrderRequest;
import org.springframework.http.ResponseEntity;

public interface ImplOrderService {
    public ResponseEntity<?> orderFoods(OrderRequest orderRequest);
    public ResponseEntity<?> getOrderDetails(String userName);
    public ResponseEntity<?> getAllOrder();
    public ResponseEntity<?> confirmPaymentRequest(ConfirmPaymentRequest request);
    public ResponseEntity<?> deleteOrder(Integer orderID);
    public ResponseEntity<?> getOrderDetailsByOrderID(Integer orderID);
    public ResponseEntity<?> getNewOrder();
    public ResponseEntity<?> cancelOrder(CancelRequest cancelRequest);
    public ResponseEntity<?> confirmCancelOrder(Integer orderDetailID);
    public ResponseEntity<?> getAllCancelOrderDetails();
    public ResponseEntity<?> getAllOrderDetail();
    public ResponseEntity<?> deleteOrderDetail(Integer orderDetailID);
    public ResponseEntity<?> cancelOrderDetail(Integer orderDetailID);
}
