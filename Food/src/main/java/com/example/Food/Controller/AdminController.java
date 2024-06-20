package com.example.Food.Controller;

import com.example.Food.DTO.Request.Admin.UpdateUserRequest;
import com.example.Food.DTO.Request.ClientRequest.UpdateFoodDetailRequest;
import com.example.Food.Repository.FoodsRepository;
import com.example.Food.Service.Comment.CommentService;
import com.example.Food.Service.Foods.FoodsService;
import com.example.Food.Service.User.UserCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
