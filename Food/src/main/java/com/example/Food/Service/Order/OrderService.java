package com.example.Food.Service.Order;

import com.example.Food.DTO.Request.ClientRequest.OrderRequest;
import com.example.Food.DTO.Response.OrderResponse;
import com.example.Food.Entity.Cart.Carts;
import com.example.Food.Entity.Cart.Promote;
import com.example.Food.Entity.Order.Orders;
import com.example.Food.Entity.User.User;
import com.example.Food.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class OrderService implements ImplOrderService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private PromoteRepository promoteRepository;




    public ResponseEntity<?> orderFoods(OrderRequest orderRequest) {
        if(orderRequest.getUserName().isEmpty()){
            return new ResponseEntity<>("not exist userName",HttpStatus.NOT_FOUND);
        }
        Optional<User> user = userRepository.FindByName(orderRequest.getUserName());
        if(orderRequest.getName().isEmpty()|| orderRequest.getAddress().isEmpty()|| orderRequest.getPhone().isEmpty() ){
            return new ResponseEntity<>("Please fill in the information",HttpStatus.NOT_FOUND);
        }
        if(user.isEmpty()){
            return new ResponseEntity<>("not exist user",HttpStatus.NOT_FOUND);
        }
        Optional<Carts> cart = cartRepository.findCartByUserName(orderRequest.getUserName());
        if(cart.isEmpty()){
            return new ResponseEntity<>("not exist cart",HttpStatus.NOT_FOUND);
        }


        Orders order = new Orders();
        order.setOrderDate(LocalDate.now());
        order.setAddress(orderRequest.getAddress());
        order.setNumberPhone(orderRequest.getPhone());
        order.setUser(user.get());
        order.setStatus(false);
        if(orderRequest.getPromoteCode().isEmpty()){
            order.setTotal(cart.get().getTotal());
        }
        else {
            Optional<Promote> promote = promoteRepository.findPromote(orderRequest.getPromoteCode());
            if(promote.isEmpty()){
                return new ResponseEntity<>("not exist promote",HttpStatus.NOT_FOUND);
            }
            Double total = cart.get().getTotal()- cart.get().getTotal()*promote.get().getPercent();
            order.setTotal(total);
        }
        order.setName(orderRequest.getName());
        ordersRepository.save(order);

        OrderResponse orderResponse;
        orderResponse = OrderResponse.builder()
                .total(order.getTotal())
                .orderID(order.getOrderID())
                .phone(order.getNumberPhone())
                .address(order.getAddress())
                .userName(order.getName())
                .build();
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

}
