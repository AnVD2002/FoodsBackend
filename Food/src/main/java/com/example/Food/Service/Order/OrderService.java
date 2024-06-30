package com.example.Food.Service.Order;

import com.example.Food.DTO.Request.ClientRequest.CancelRequest;
import com.example.Food.DTO.Request.ClientRequest.ConfirmPaymentRequest;
import com.example.Food.DTO.Request.ClientRequest.OrderRequest;
import com.example.Food.DTO.Response.Admin.OrderAdminResponse;
import com.example.Food.DTO.Response.OrderDetailAdminResponse;
import com.example.Food.DTO.Response.OrderDetailCancelResponse;
import com.example.Food.DTO.Response.OrderDetailResponse;
import com.example.Food.Entity.Cart.CartItems;
import com.example.Food.Entity.Cart.Carts;
import com.example.Food.Entity.Cart.Promote;
import com.example.Food.Entity.Food.FoodDetails;
import com.example.Food.Entity.Order.CancelOrderDetail;
import com.example.Food.Entity.Order.OrderDetails;
import com.example.Food.Entity.Order.Orders;
import com.example.Food.Entity.Payment.Payment;
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
    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private FoodDetailsRepository foodDetailsRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CancelRepository cancelRepository;

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
        List<CartItems> cartItems = cart.get().getCartItems();
        List<Integer> cartItemIds = new ArrayList<>();
        for (CartItems cartItem : cartItems) {
            cartItemIds.add(cartItem.getCartItemID());
        }
        List<Integer> foodDetailIDs = cartItemsRepository.findFoodDetailIDByCartItemID(cartItemIds);
        if(foodDetailIDs.isEmpty()){
            return new ResponseEntity<>("not exist foodDetailID",HttpStatus.NOT_FOUND);
        }
        List<FoodDetails> foodDetails= foodDetailsRepository.findByFoodDetailIDs(foodDetailIDs);
        if(foodDetails.isEmpty()){
            return new ResponseEntity<>("not exist foodDetails",HttpStatus.NOT_FOUND);
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
        List<OrderDetails> orderDetails = new ArrayList<>();
        for(FoodDetails foodDetail : foodDetails){
            OrderDetails orderDetail = new OrderDetails();
            orderDetail.setFoodDetail(foodDetail);
            orderDetail.setOrder(order);
            orderDetail.setQuantity(foodDetail.getCartItem().getQuantity());
            orderDetail.setTotal(foodDetail.getCartItem().getPrice() * foodDetail.getCartItem().getQuantity()) ;
            orderDetails.add(orderDetail);
        }
        orderDetailsRepository.saveAll(orderDetails);
        OrderAdminResponse orderResponse;
        orderResponse = OrderAdminResponse.builder()
                .total(order.getTotal())
                .orderID(order.getOrderID())
                .phone(order.getNumberPhone())
                .address(order.getAddress())
                .userName(order.getName())
                .build();
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getOrderDetails(String userName) {
        if(userName.isEmpty()){
            return new ResponseEntity<>("not exist userID",HttpStatus.NOT_FOUND);
        }
        Optional<User> user = userRepository.FindByName(userName);
        if(user.isEmpty()){
            return new ResponseEntity<>("not exist user",HttpStatus.NOT_FOUND);
        }
        List<Orders> orders = user.get().getOrders();

        if(orders.isEmpty()){
            return new ResponseEntity<>("not exist order",HttpStatus.NOT_FOUND);
        }
        List<Integer> orderIDs = orders.stream()
                .map(Orders::getOrderID)
                .toList();
        List<OrderDetails> orderDetails = orderDetailsRepository.findByOrderIDs(orderIDs);
        if(orderDetails.isEmpty()){
            return new ResponseEntity<>("not exist orderDetails",HttpStatus.NOT_FOUND);
        }
        List<OrderDetailResponse> orderDetailResponses = orderDetails.stream()
                .map(orderDetail -> new OrderDetailResponse(
                        orderDetail.getOrderDetailID(),
                        orderDetail.getFoodDetail().getFoodDetailName(),
                        orderDetail.getTotal(),
                        orderDetail.getQuantity(),
                        orderDetail.getFoodDetail().getFoodDetailsPropertyDetails().get(0).getFood().getImage(),
                        orderDetail.getOrder().isStatus(),
                        orderDetail.getOrder().getPayment() != null && orderDetail.getOrder().getPayment().isStatus(),
                        orderDetail.getCancelOrderDetail()!=null && orderDetail.getCancelOrderDetail().isStatus()
                ))
                .toList();

        return new ResponseEntity<>(orderDetailResponses, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> getAllOrder(){
        List<Orders> orders = ordersRepository.findAll();
        if(orders.isEmpty()){
            return new ResponseEntity<>("not exist order",HttpStatus.NOT_FOUND);
        }
        List<OrderAdminResponse> responses = orders.stream()
                .map(order -> {
                    boolean paymentStatus = order.getPayment() != null && order.getPayment().isStatus();
                    return new OrderAdminResponse(
                            order.getOrderID(),
                            order.getUser().getUserName(),
                            order.getName(),
                            order.getAddress(),
                            order.getNumberPhone(),
                            order.getTotal(),
                            order.isStatus(),
                            paymentStatus,
                            order.getOrderDate()
                    );
                })
                .toList();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
    public ResponseEntity<?> confirmPaymentRequest(ConfirmPaymentRequest request){
        if (request.getOrderID()==null){
            return new ResponseEntity<>("not exist orderID",HttpStatus.NOT_FOUND);
        }
        Optional<Orders> order = ordersRepository.findById(request.getOrderID());
        if(order.isEmpty()){
            return new ResponseEntity<>("not exist order",HttpStatus.NOT_FOUND);
        }
        order.get().setStatus(true);
        if(order.get().getPayment()==null){
            return new ResponseEntity<>("not exist payment",HttpStatus.NOT_FOUND);
        }
        order.get().getPayment().setStatus(true);
        ordersRepository.save(order.get());
        return new ResponseEntity<>("confirm payment success",HttpStatus.OK);
    }
    public ResponseEntity<?> deleteOrder(Integer orderID){
        if(orderID==null){
            return new ResponseEntity<>("not exist orderID",HttpStatus.NOT_FOUND);
        }
        Optional<Orders> order = ordersRepository.findById(orderID);
        if(order.isEmpty()){
            return new ResponseEntity<>("not exist order",HttpStatus.NOT_FOUND);
        }
        List<OrderDetails> orderDetails = order.get().getOrderDetails();
        if (!orderDetails.isEmpty()) {
            orderDetailsRepository.deleteAll(orderDetails);
        }
        Payment payment = order.get().getPayment();
        if (payment != null) {
            paymentRepository.delete(payment);
        }
        ordersRepository.delete(order.get());

        return new ResponseEntity<>("delete order success",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getOrderDetailsByOrderID(Integer orderID) {
        if(orderID==null){
            return new ResponseEntity<>("not exist orderID",HttpStatus.NOT_FOUND);
        }
        Optional<Orders> order = ordersRepository.findById(orderID);
        if(order.isEmpty()){
            return new ResponseEntity<>("not exist order",HttpStatus.NOT_FOUND);
        }
        List<OrderDetails> orderDetails = order.get().getOrderDetails();
        if(orderDetails.isEmpty()){
            return new ResponseEntity<>("not exist orderDetails",HttpStatus.NOT_FOUND);
        }
        List<OrderDetailResponse> orderDetailResponses = orderDetails.stream()
                .map(orderDetail -> new OrderDetailResponse(
                        orderDetail.getOrderDetailID(),
                        orderDetail.getFoodDetail().getFoodDetailName(),
                        orderDetail.getTotal(),
                        orderDetail.getQuantity(),
                        orderDetail.getFoodDetail().getFoodDetailsPropertyDetails().get(0).getFood().getImage(),
                        orderDetail.getOrder().isStatus(),
                        orderDetail.getOrder().getPayment() != null && orderDetail.getOrder().getPayment().isStatus(),
                        orderDetail.getCancelOrderDetail()!=null && orderDetail.getCancelOrderDetail().isStatus()
                ))
                .toList();
        return new ResponseEntity<>(orderDetailResponses, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> getNewOrder(){
        List<Orders> orders = ordersRepository.findNewOrders();
        if(orders.isEmpty()){
            return new ResponseEntity<>("Not exist new order",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orders.size(), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> cancelOrder(CancelRequest cancelRequest){
        if(cancelRequest.getOrderDetailID()==null){
            return new ResponseEntity<>("not exist orderDetailID",HttpStatus.NOT_FOUND);
        }
        if(cancelRequest.getReason().isEmpty()){
            return new ResponseEntity<>("not exist reason",HttpStatus.NOT_FOUND);
        }
        Optional<OrderDetails> orderDetail = orderDetailsRepository.findById(cancelRequest.getOrderDetailID());
        if(orderDetail.isEmpty()){
            return new ResponseEntity<>("not exist orderDetail",HttpStatus.NOT_FOUND);
        }
        CancelOrderDetail cancelOrderDetail = new CancelOrderDetail();
        cancelOrderDetail.setOrder_detail(orderDetail.get());
        cancelOrderDetail.setReason(cancelRequest.getReason());
        cancelOrderDetail.setStatus(true);
        cancelRepository.save(cancelOrderDetail);
        orderDetail.get().setCancelOrderDetail(cancelOrderDetail);
        orderDetailsRepository.save(orderDetail.get());
        return new ResponseEntity<>("Đang chờ xử lí ", HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> confirmCancelOrder(Integer orderDetailID){
        if(orderDetailID==null){
            return new ResponseEntity<>("not exist orderDetailID",HttpStatus.NOT_FOUND);
        }
        Optional<OrderDetails> orderDetail = orderDetailsRepository.findById(orderDetailID);
        if(orderDetail.isEmpty()){
            return new ResponseEntity<>("not exist orderDetail",HttpStatus.NOT_FOUND);
        }
        CancelOrderDetail cancelOrderDetail = orderDetail.get().getCancelOrderDetail();
        if(cancelOrderDetail==null){
            return new ResponseEntity<>("not exist cancelOrderDetail",HttpStatus.NOT_FOUND);
        }
        cancelOrderDetail.setStatus(true);
        cancelRepository.save(cancelOrderDetail);
        return new ResponseEntity<>("confirm cancel order success",HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> getAllCancelOrderDetails(){
        List<OrderDetails> orderDetails = orderDetailsRepository.findOrderDetailsWithNonEmptyCancelOrderDetail();
        if(orderDetails.isEmpty()){
            return new ResponseEntity<>("not exist orderDetails",HttpStatus.NOT_FOUND);
        }
        List<OrderDetailCancelResponse> response = orderDetails
                .stream()
                .map(orderDetail -> {
                    CancelOrderDetail cancelOrderDetail = orderDetail.getCancelOrderDetail();
                    String reason = (cancelOrderDetail != null) ? cancelOrderDetail.getReason() : null;
                    return new OrderDetailCancelResponse(
                            orderDetail.getOrderDetailID(),
                            cancelOrderDetail != null ? cancelOrderDetail.getCancel_id() : null,
                            orderDetail.getOrder().getName(),
                            orderDetail.getTotal(),
                            orderDetail.getQuantity(),
                            orderDetail.getFoodDetail().getFoodDetailsPropertyDetails().get(0).getFood().getImage(),
                            orderDetail.getOrder().isStatus(),
                            orderDetail.getOrder().getPayment() != null && orderDetail.getOrder().getPayment().isStatus(),
                            reason
                    );
                })
                .toList();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
        @Override
        public ResponseEntity<?> getAllOrderDetail(){
        List<OrderDetails> orderDetails = orderDetailsRepository.findAll();
        if(orderDetails.isEmpty()){
            return new ResponseEntity<>("not exist orderDetails",HttpStatus.NOT_FOUND);
        }
        List<OrderDetailAdminResponse> response = orderDetails
                .stream()
                .map(orderDetail -> {
                    CancelOrderDetail cancelOrderDetail = orderDetail.getCancelOrderDetail();
                    String reason = (cancelOrderDetail != null) ? cancelOrderDetail.getReason() : null;
                    boolean isCancelled = (cancelOrderDetail != null) && cancelOrderDetail.isStatus();
                    return new OrderDetailAdminResponse(
                            orderDetail.getOrderDetailID(),
                            orderDetail.getOrder().getUser().getUserID(),
                            orderDetail.getOrder().getOrderID(),
                            orderDetail.getOrder().getName(),
                            orderDetail.getTotal(),
                            orderDetail.getQuantity(),
                            orderDetail.getFoodDetail().getFoodDetailsPropertyDetails().get(0).getFood().getImage(),
                            orderDetail.getOrder().isStatus(),
                            orderDetail.getOrder().getPayment() != null && orderDetail.getOrder().getPayment().isStatus(),
                            isCancelled,
                            reason
                    );
                })
                .toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteOrderDetail(Integer orderDetailID){
        if (orderDetailID == null) {
            return new ResponseEntity<>("not exist orderDetailID",HttpStatus.NOT_FOUND);
        }
        Optional<OrderDetails> orderDetail = orderDetailsRepository.findById(orderDetailID);
        if(orderDetail.isEmpty()){
            return new ResponseEntity<>("not exist orderDetail",HttpStatus.NOT_FOUND);
        }
        orderDetailsRepository.delete(orderDetail.get());
        orderDetailsRepository.flush();
        return new ResponseEntity<>("delete orderDetail success",HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> cancelOrderDetail(Integer orderDetailID){
        if (orderDetailID == null) {
            return new ResponseEntity<>("not exist orderDetailID",HttpStatus.NOT_FOUND);
        }
        Optional<OrderDetails> orderDetail = orderDetailsRepository.findById(orderDetailID);
        if(orderDetail.isEmpty()){
            return new ResponseEntity<>("not exist orderDetail",HttpStatus.NOT_FOUND);
        }
        FoodDetails foodDetail = orderDetail.get().getFoodDetail();
        if (foodDetail == null) {
            return new ResponseEntity<>("not exist foodDetail",HttpStatus.NOT_FOUND);
        }
        foodDetail.setOrdered(foodDetail.getOrdered() - orderDetail.get().getQuantity());
        foodDetail.setQuantity(foodDetail.getQuantity() + orderDetail.get().getQuantity());

        foodDetailsRepository.save(foodDetail);
        orderDetailsRepository.delete(orderDetail.get());
        orderDetailsRepository.flush();
        return new ResponseEntity<>("cancel orderDetail success",HttpStatus.OK);


    }
}
