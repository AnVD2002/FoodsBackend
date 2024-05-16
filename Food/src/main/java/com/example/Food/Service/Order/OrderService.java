package com.example.Food.Service.Order;

import com.example.Food.Config.VNPayConfig;
import com.example.Food.DTO.Request.BuyRequest;
import com.example.Food.DTO.Request.TransactionalRequest;
import com.example.Food.DTO.Response.PaymentResponse;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class OrderService implements ImplOrderService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private FoodDetailsRepository foodDetailsRepository;


    @Override
    public PaymentResponse apiVNPay(HttpServletRequest request, TransactionalRequest transactionRequest) {
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
        if ("NCB" != null && !"NCB".isEmpty()) {
            vnp_Params.put("vnp_BankCode", "NCB");
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thông tin đặt hàng");
        vnp_Params.put("vnp_OrderType", "Chuyển khoản");

        if ("vn" != null && !"vn".isEmpty()) {
            vnp_Params.put("vnp_Locale", "vn");
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
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
        return PaymentResponse.builder().url("value" + VNPayConfig.vnp_PayUrl + "?" + queryUrl).build();
    }
    @Transactional
    @Override
    public ResponseEntity<?> buyFoods(BuyRequest buyRequest) {
        Optional<User> user = userRepository.findById(buyRequest.getUserID());
        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Carts> cart = cartRepository.findCartByUserID(buyRequest.getUserID());
        if(cart.isEmpty()){
            return new ResponseEntity<>("not exist cart", HttpStatus.NOT_FOUND);
        }
        List<CartItems> cartItemsList= cart.get().getCartItems();
        double sum =0;
        for (CartItems cartItems : cartItemsList) {
            sum += cartItems.getQuantity() * cartItems.getPrice();
            Optional<FoodDetails> foodDetail = foodDetailsRepository.findById(cartItems.getFoodDetailID());
            if(foodDetail.isEmpty()){
                return new ResponseEntity<>("not exist foodDetail", HttpStatus.NOT_FOUND);
            }
            if(foodDetail.get().getQuantity()<cartItems.getQuantity()){
                return new ResponseEntity<>("not enough foods", HttpStatus.NOT_FOUND);
            }
            foodDetail.get().setQuantity(foodDetail.get().getQuantity() - cartItems.getQuantity());
            foodDetailsRepository.save(foodDetail.get());
        }
        Orders order = new Orders();
        order.setOrderDate(LocalDate.now());
        order.setAddress(buyRequest.getAddress());
        order.setName(buyRequest.getName());
        order.setNumberPhone(buyRequest.getPhone());
        order.setUser(user.get());
        order.setStatus(false);

        ordersRepository.save(order);

        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(buyRequest.getPaymentMethodID());
        if(paymentMethod.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Payment payment = new Payment();
        payment.setPaymentDate(LocalDate.now());
        payment.setStatus(false);
        payment.setPaymentMethod(paymentMethod.get());
        payment.setTotal(sum);
        payment.setOrder(order);
        paymentRepository.save(payment);
        return new ResponseEntity<>(sum, HttpStatus.OK);
    }
}
