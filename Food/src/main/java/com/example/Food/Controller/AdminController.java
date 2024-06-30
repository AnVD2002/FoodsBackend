package com.example.Food.Controller;

import com.example.Food.DTO.Request.Admin.UpdateUserRequest;
import com.example.Food.DTO.Request.ClientRequest.*;
import com.example.Food.Repository.FoodsRepository;
import com.example.Food.Repository.PaymentRepository;
import com.example.Food.Service.Comment.CommentService;
import com.example.Food.Service.Foods.FoodsService;
import com.example.Food.Service.Foods.PropertiesCRUD.PropertiesService;
import com.example.Food.Service.Foods.PropertyDetailCRUD.PropertyDetailService;
import com.example.Food.Service.Order.OrderService;
import com.example.Food.Service.Payment.PaymentService;
import com.example.Food.Service.User.UserCustomService;
import com.example.Food.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

@RequestMapping("/api/v1/admin/")
@RestController
public class AdminController {
    @Autowired
    private FoodsService foodsService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private FoodsRepository foodsRepository;
    @Autowired
    private UserCustomService userCustomService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PropertiesService propertiesService;
    @Autowired
    private PropertyDetailService propertyDetailService;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentService paymentService;

    @CrossOrigin
    @GetMapping(path = "foodDetailByID")
    private ResponseEntity<?> foodDetailByID(@RequestParam Integer foodID){
        return ResponseEntity.ok(foodsService.getFoodDetailByID(foodID));
    }
    @CrossOrigin
    @PutMapping(path = "updateFoodDetail")
    private ResponseEntity<?> updateFoodDetail(@RequestBody UpdateFoodDetailRequest request){
        return ResponseEntity.ok(foodsService.updateFoodDetail(request));
    }
    @CrossOrigin
    @DeleteMapping(path = "deleteFoodDetail")
    private ResponseEntity<?> deleteFoodDetail(@RequestParam Integer foodDetailID){
        return ResponseEntity.ok(foodsService.deleteFoodDetail(foodDetailID));
    }
    @CrossOrigin
    @GetMapping(path = "getAllUser")
    private ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(userCustomService.getAllUser());
    }
    @CrossOrigin
    @DeleteMapping(path = "deleteUser")
    private ResponseEntity<?> deleteUser(@RequestParam Integer userID){
        return ResponseEntity.ok(userCustomService.deleteUser(userID));
    }
    @CrossOrigin
    @PutMapping(path = "updateUser")
    private ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest request){
        return ResponseEntity.ok(userCustomService.updateUser(request));
    }
    @CrossOrigin
    @DeleteMapping(path = "deleteOrder")
    public ResponseEntity<?> deleteOrder(@RequestParam Integer orderID){
        return ResponseEntity.ok(orderService.deleteOrder(orderID));
    }
    @CrossOrigin
    @DeleteMapping(path = "deletePropertyDetail")
    public ResponseEntity<?> deletePropertyDetail(@RequestParam Integer propertyID){
        return ResponseEntity.ok(propertyDetailService.deletePropertyDetail(propertyID));
    }
    @CrossOrigin
    @GetMapping(path = "getAllCategory")
    private ResponseEntity<?> getAllCategory(){
        return ResponseEntity.ok(foodsService.getAllCategory());
    }
    @CrossOrigin
    @GetMapping(path = "getAllProperty")
    private ResponseEntity<?> getAllProperty(){
        return ResponseEntity.ok(foodsService.getAllProperties());
    }
    @CrossOrigin
    @PostMapping(path = "addProperty")
    public ResponseEntity<?> addProperty(@RequestParam String name){
        return ResponseEntity.ok(propertiesService.addProperties(name));
    }
    @CrossOrigin
    @PutMapping(path = "updateProperties")
    public ResponseEntity<?> updateProperties(@RequestBody UpdatePropertiesRequest request){
        return ResponseEntity.ok(propertiesService.updateProperties(request));
    }
    @CrossOrigin
    @DeleteMapping(path = "deleteProperties")
    public ResponseEntity<?> deleteProperties(@RequestParam Integer propertyID){
        return ResponseEntity.ok(propertiesService.deleteProperties(propertyID));
    }
    @CrossOrigin
    @PostMapping(path = "addPropertyDetail")
    public ResponseEntity<?> addPropertyDetail(@RequestBody AddPropertyDetailRequest request){
        return ResponseEntity.ok(propertyDetailService.addPropertyDetail(request));
    }
    @CrossOrigin
    @PutMapping(path = "updatePropertyDetail")
    public ResponseEntity<?> updatePropertyDetail(@RequestBody UpdatePropertyDetailRequest request){
        return ResponseEntity.ok(propertyDetailService.updatePropertyDetail(request));
    }
    @CrossOrigin
    @PostMapping(path = "foodFilter")
    public ResponseEntity<?> foodFilter(@RequestBody FoodFilterRequest foodFilterRequest){
        return ResponseEntity.ok(foodsService.getFoodDetailsFilter(foodFilterRequest));
    }
    @CrossOrigin
    @GetMapping(path = "allFoodCategories")
    public ResponseEntity<?> allFoodCategories(){
        return ResponseEntity.ok(foodsService.getAllFoodCategories());
    }
    @CrossOrigin
    @DeleteMapping(path = "deleteFood")
    public ResponseEntity<?> DeleteFood(@RequestParam Integer  foodID){
        return ResponseEntity.ok(foodsService.deleteFood(foodID));
    }
    @CrossOrigin
    @PostMapping(path = "createFoodPropertyDetails",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> CreateFoodPropertyDetails(@RequestBody FoodPropertyDetailsRequest request){
        return ResponseEntity.ok(foodsService.createFoodWithPropertiesDetails(request));
    }
    @CrossOrigin
    @PostMapping(path = "createFoodProperty",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> CreateFoodProperty(@RequestBody FoodRequest request){
        return ResponseEntity.ok(foodsService.CreateFoodWithProperties(request));
    }
    @CrossOrigin
    @GetMapping(path = "getAllOrder")
    private ResponseEntity<?> getAllOrder(){
        return ResponseEntity.ok(orderService.getAllOrder());
    }
    @CrossOrigin
    @PutMapping(path = "confirmPayment")
    public ResponseEntity<?> confirmPayment(@RequestBody ConfirmPaymentRequest request){
        return ResponseEntity.ok(orderService.confirmPaymentRequest(request));
    }
    @CrossOrigin
    @GetMapping(path = "getOrderDetailByOrderID")
    private ResponseEntity<?> getOrderDetailByOrderID(@RequestParam Integer orderID){
        return ResponseEntity.ok(orderService.getOrderDetailsByOrderID(orderID));
    }
    @CrossOrigin
    @GetMapping(path = "propertyDetailByFoodID")
    public ResponseEntity<?> getPropertyDetailFoodID(@RequestParam Integer foodID){
        return ResponseEntity.ok(foodsService.getPropertyDetailByFoodID(foodID));
    }
    @CrossOrigin
    @GetMapping(path = "getPropertyDetailByID")
    public ResponseEntity<?> getPropertyDetailByID(@RequestParam Integer propertyID){
        return ResponseEntity.ok(propertyDetailService.getPropertyDetailByID(propertyID));
    }
    @CrossOrigin
    @GetMapping(path = "getTotalInThisMonth")
    public ResponseEntity<?> GetTotalInThisMonth(){
        return ResponseEntity.ok(paymentService.getToTalInThisMonth());
    }
    @CrossOrigin
    @GetMapping(path = "getQuantityInThisMonth")
    public ResponseEntity<?> getQuantityInThisMonth(){
        return ResponseEntity.ok(paymentService.getQuantityThisMonth());
    }
    @CrossOrigin
    @GetMapping(path = "getNewOrder")
    public ResponseEntity<?> getNewOrder(){
        return ResponseEntity.ok(orderService.getNewOrder());
    }
    @CrossOrigin
    @GetMapping(path = "getTotalUser")
    public ResponseEntity<?> getTotalUser(){
        return ResponseEntity.ok(userCustomService.getTotalUser());
    }
    @CrossOrigin
    @PutMapping(path = "updateFoods")
    public ResponseEntity<?> updateFoods(@RequestBody UpdateFoods foodUpdateRequest){
        return ResponseEntity.ok(foodsService.updateFood(foodUpdateRequest));
    }
    @CrossOrigin
    @GetMapping("getAllOrderDetail")
    public ResponseEntity<?> getAllOrderDetail(){
        return ResponseEntity.ok(orderService.getAllOrderDetail());
    }
    @CrossOrigin
    @DeleteMapping("deleteOrderDetails")
    public ResponseEntity<?> deleteOrderDetail(@RequestParam Integer orderDetailID){
        return ResponseEntity.ok(orderService.deleteOrderDetail(orderDetailID));
    }
    @CrossOrigin
    @DeleteMapping("cancelOrderDetailClient")
    public ResponseEntity<?> cancelOrderDetailClient(@RequestParam Integer orderDetailID)
    {
        return ResponseEntity.ok(orderService.cancelOrderDetail(orderDetailID));
    }

}
