package com.example.Food.Service.Payment;

import com.example.Food.Config.VNPayConfig;
import com.example.Food.DTO.Request.ClientRequest.PaymentRequest;
import com.example.Food.DTO.Request.ClientRequest.TransactionalRequest;
import com.example.Food.DTO.Response.PaymentResponse;
import com.example.Food.DTO.Response.VnPay_Response;
import com.example.Food.Entity.Cart.CartItems;
import com.example.Food.Entity.Cart.Carts;
import com.example.Food.Entity.Food.FoodDetails;
import com.example.Food.Entity.Order.Orders;
import com.example.Food.Entity.Payment.Payment;
import com.example.Food.Entity.Payment.PaymentMethod;
import com.example.Food.Entity.User.User;
import com.example.Food.Repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class PaymentService implements ImplPaymentService {
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

    @Transactional
    public ResponseEntity<?> payment(PaymentRequest paymentRequest) {
        Logger logger = LoggerFactory.getLogger(PaymentService.class);

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
        vnp_Params.put("vnp_OrderInfo", "Thông tin đặt hàng");
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


}
