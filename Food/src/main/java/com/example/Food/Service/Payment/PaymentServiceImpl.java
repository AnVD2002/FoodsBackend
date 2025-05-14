package com.example.Food.Service.Payment;

import com.example.Food.Config.VNPayConfig;
import com.example.Food.DTO.Request.ClientRequest.PaymentRequest;
import com.example.Food.DTO.Request.ClientRequest.TransactionalRequest;
import com.example.Food.DTO.Request.ClientRequest.VNPayRequest;
import com.example.Food.DTO.Response.PaymentResponse;
import com.example.Food.DTO.Response.VnPay_Response;
import com.example.Food.Entity.Cart.CartItems;
import com.example.Food.Entity.Cart.Carts;
import com.example.Food.Entity.Order.OrderDetails;
import com.example.Food.Entity.Order.Orders;
import com.example.Food.Entity.Payment.Payment;
import com.example.Food.Entity.Payment.PaymentMethod;
import com.example.Food.Entity.User.User;
import com.example.Food.Repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private FoodDetailsRepository foodDetailsRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Transactional
    public ResponseEntity<?> payment(PaymentRequest paymentRequest) {

        Optional<Orders> order = ordersRepository.findById(paymentRequest.getOrderID());
        if (order.isEmpty()) {
            return new ResponseEntity<>("Không tồn tại orderID", HttpStatus.NOT_FOUND);
        }
        Orders orders = order.get();
        User user = orders.getUser();
        Carts cart = user.getCart();

        Payment payment = new Payment();
        payment.setOrder(orders);
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(paymentRequest.getPaymentID());
        if (paymentMethod.isEmpty()) {
            return new ResponseEntity<>("Không tồn tại paymentID", HttpStatus.NOT_FOUND);
        }
        payment.setPaymentMethod(paymentMethod.get());
        payment.setPaymentDate(LocalDate.now());
        List<CartItems> list = cart.getCartItems();
        if (list.isEmpty()) {
            return new ResponseEntity<>("Không tồn tại cartItems", HttpStatus.NOT_FOUND);
        }
        List<Integer> integerList = cartItemsRepository.findCartItemByCartID(cart.getCartID());
        if(integerList.isEmpty()) {
            return new ResponseEntity<>("không tồn tại ", HttpStatus.NOT_FOUND);
        }
        List<OrderDetails> orderDetailsList = order.get().getOrderDetails();
        if(orderDetailsList.isEmpty()) {
            return new ResponseEntity<>("khong ton tai ", HttpStatus.NOT_FOUND);
        }
        List<OrderDetails> listOrderDetail = new ArrayList<>();
        for(OrderDetails orderDetail : orderDetailsList) {
            orderDetail.getFoodDetail().setOrdered(orderDetail.getQuantity()+ orderDetail.getFoodDetail().getOrdered());
            orderDetail.getFoodDetail().setQuantity( orderDetail.getFoodDetail().getQuantity() - orderDetail.getQuantity());
            listOrderDetail.add(orderDetail);
        }
        orderDetailsRepository.saveAll(listOrderDetail);
        paymentRepository.save(payment);
        orders.setStatus(true);
        ordersRepository.save(orders);
        cartItemsRepository.deleteAllById(integerList);
        cart.setTotal(0.0);
        cartRepository.save(cart);
        PaymentResponse paymentResponse = PaymentResponse.builder()
                .paymentDate(LocalDate.now())
                .paymentStatus(false)
                .orderID(orders.getOrderID())
                .paymentID(orders.getOrderID())
                .paymentAmount(orders.getTotal())
                .build();
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> payVNPay(VNPayRequest vnpRequest) {
        if (!"00".equals(vnpRequest.getCode())) {
            return new ResponseEntity<>("Thanh toan that bai", HttpStatus.NOT_FOUND);
        }
        if (vnpRequest.getOrderID() == null) {
            return new ResponseEntity<>("not exist orderID", HttpStatus.NOT_FOUND);
        }

        Optional<Orders> order = ordersRepository.findById(vnpRequest.getOrderID());
        if (order.isEmpty()) {
            return new ResponseEntity<>("not exist order", HttpStatus.NOT_FOUND);
        }

        Orders orders = order.get();
        User user = orders.getUser();
        Carts cart = user.getCart();

        List<CartItems> cartItems = cart.getCartItems();
        if (cartItems.isEmpty()) {
            return new ResponseEntity<>("not exist cartItem", HttpStatus.NOT_FOUND);
        }

        List<OrderDetails> orderDetailsList = orders.getOrderDetails();
        if (orderDetailsList.isEmpty()) {
            return new ResponseEntity<>("not exist orderDetails", HttpStatus.NOT_FOUND);
        }
        List<OrderDetails> listOrderDetail = new ArrayList<>();
        for (OrderDetails orderDetail : orderDetailsList) {
            orderDetail.getFoodDetail().setOrdered(orderDetail.getQuantity() + orderDetail.getFoodDetail().getOrdered());
            orderDetail.getFoodDetail().setQuantity(orderDetail.getFoodDetail().getQuantity() - orderDetail.getQuantity());
            listOrderDetail.add(orderDetail);
        }
        orderDetailsRepository.saveAll(listOrderDetail);

        orders.setStatus(true);
        ordersRepository.save(orders);

        cartItemsRepository.deleteBatch(cartItems);

        cart.setTotal(0.0);
        cartRepository.save(cart);

        Optional<PaymentMethod> paymentMT = paymentMethodRepository.findById(1);
        if (paymentMT.isEmpty()) {
            return new ResponseEntity<>("not exist paymentMethod", HttpStatus.NOT_FOUND);
        }

        Payment payment = new Payment();
        payment.setOrder(orders);
        payment.setPaymentMethod(paymentMT.get());
        payment.setStatus(true);
        payment.setPaymentDate(LocalDate.now());
        paymentRepository.save(payment);

        return new ResponseEntity<>("Thanh toan thanh cong", HttpStatus.OK);
    }



    @Override
    public VnPay_Response apiVNPay(HttpServletRequest request, TransactionalRequest transactionRequest) {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";

        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = VNPayConfig.getIpAddress(request);
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(transactionRequest.getAmountParam() * 100) );
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", transactionRequest.getOrderID().toString());
        vnp_Params.put("vnp_OrderType", "Chuyển khoản");


        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator iterator = fieldNames.iterator();
        while (iterator.hasNext()) {
            String fieldName = (String) iterator.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {

                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (iterator.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        return VnPay_Response.builder().url( VNPayConfig.vnp_PayUrl + "?" + queryUrl).build();
    }
    
    @Override
    public ResponseEntity<?> getToTalInThisMonth() {
        Double total = paymentRepository.findTotalForCurrentMonth();
        if(total == null) {
            return new ResponseEntity<>("Tháng này kh có đơn hàng nào",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(total, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> getQuantityThisMonth(){
        List<Orders> orders = paymentRepository.findOrderForCurrentMonth();
        if(orders.isEmpty()) {
            return new ResponseEntity<>("not exist", HttpStatus.NOT_FOUND);
        }
        int totalQuantity = 0;
        for (Orders order : orders) {
            List<OrderDetails> orderDetailsList = order.getOrderDetails();
            totalQuantity += orderDetailsList.stream()
                    .mapToInt(OrderDetails::getQuantity)
                    .sum();
        }
        return new ResponseEntity<>(totalQuantity, HttpStatus.OK);
    }






}
