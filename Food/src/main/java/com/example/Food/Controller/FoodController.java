package com.example.Food.Controller;

import com.example.Food.DTO.Request.ClientRequest.*;
import com.example.Food.DTO.Request.User.CommentRequest;
import com.example.Food.DTO.Request.User.RepCommentRequest;
import com.example.Food.Entity.Food.Foods;
import com.example.Food.Repository.FoodsRepository;
import com.example.Food.Service.Comment.CommentServiceImpl;
import com.example.Food.Service.Foods.FoodsServiceImpl;
import com.example.Food.Service.Foods.PropertiesCRUD.PropertiesServiceImpl;
import com.example.Food.Service.Foods.PropertyDetailCRUD.PropertyDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/v1/food/")
@RestController
public class FoodController {
    @Autowired
    private FoodsServiceImpl foodsService;
    @Autowired
    private CommentServiceImpl commentService;
    @Autowired
    private FoodsRepository foodsRepository;
    @Autowired
    private PropertyDetailServiceImpl propertyDetailService;
    @Autowired
    private PropertiesServiceImpl propertiesService;

    @CrossOrigin
    @PostMapping(path = "createFoodProperty",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> CreateFoodProperty(@RequestBody FoodRequest request){
        return ResponseEntity.ok(foodsService.CreateFoodWithProperties(request));
    }
    @CrossOrigin
    @PutMapping(path = "updateFood", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateFood(@RequestParam FoodUpdateRequest foodUpdateRequest){
        return ResponseEntity.ok(foodsService.updateFood(foodUpdateRequest));
    }
    @CrossOrigin
    @GetMapping(path = "allFood")
    public ResponseEntity<?> allFood(){
        return ResponseEntity.ok(foodsService.getAllFoods());
    }

    @CrossOrigin
    @GetMapping(path = "allPropertyDetail")
    public ResponseEntity<?> allPropertyDetail(){
        return ResponseEntity.ok(foodsService.allPropertyDetails());
    }
    @CrossOrigin
    @GetMapping(path = "propertyDetailByFoodID")
    public ResponseEntity<?> getPropertyDetailFoodID(@RequestParam Integer foodID){
        return ResponseEntity.ok(foodsService.getPropertyDetailByFoodID(foodID));
    }
    @CrossOrigin
    @PostMapping(path = "comment")
    public ResponseEntity<?> postComment(@RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.comment(commentRequest));
    }
    @CrossOrigin
    @PostMapping(path = "repComment")
    public ResponseEntity<?> postRepComment(@RequestBody RepCommentRequest repCommentRequest) {
        return ResponseEntity.ok(commentService.repComment(repCommentRequest));
    }
    @CrossOrigin
    @GetMapping(path = "commentByFoodID")
    public ResponseEntity<?> getCommentByFoodID(@RequestParam Integer foodID) {
        return ResponseEntity.ok(commentService.getCommentByFoodID(foodID));
    }
    @CrossOrigin
    @GetMapping(path = "commentResponseByFoodID")
    public ResponseEntity<?> getResponseCommentByFoodID(@RequestParam Integer foodID) {
        return ResponseEntity.ok(commentService.getResponseCommentByFoodID(foodID));
    }
    @CrossOrigin
    @DeleteMapping(path = "deleteComment")
    public ResponseEntity<?> deleteComment(@RequestParam Integer commentID){
        return ResponseEntity.ok(commentService.deleteComment(commentID));
    }
    @CrossOrigin
    @PutMapping(path = "updateComment")
    public ResponseEntity<?> updateComment(@RequestBody UpdateCommentRequest updateCommentRequest){
        return ResponseEntity.ok(commentService.updateComment(updateCommentRequest));
    }
    @CrossOrigin
    @GetMapping(path = "getFoodDetail")
    public ResponseEntity<?> getFoodDetail(@RequestBody FoodDetailRequest foodDetailRequest){
        return ResponseEntity.ok(foodsService.getFoodDetailB(foodDetailRequest));
    }
    @CrossOrigin
    @GetMapping(path = "rating")
    public ResponseEntity<?> getRating(@RequestParam Integer foodID){
        Optional<Foods> food = foodsRepository.findById(foodID);
        if(food.isEmpty()) {
            return new ResponseEntity<>("not exist food", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(commentService.findRatingByFoodID(foodID));
    }
    @CrossOrigin
    @PostMapping(path = "foodFilterClient")
    public ResponseEntity<?> foodFilterClient(@RequestBody FoodDetailClientRequest foodDetailClientRequest){
        return ResponseEntity.ok(foodsService.getFoodDetailsFilterClient(foodDetailClientRequest));
    }
    @CrossOrigin
    @GetMapping(path = "foodDetailByID")
    private ResponseEntity<?> foodDetailByID(@RequestParam Integer foodID){
        return ResponseEntity.ok(foodsService.getFoodDetailByID(foodID));
    }
    @CrossOrigin
    @GetMapping(path = "getTopOrder")
    public ResponseEntity<?> getTopOrder(){
        return ResponseEntity.ok(foodsService.getTopOrder());
    }
    @CrossOrigin
    @GetMapping(path = "allFoodCategories")
    public ResponseEntity<?> allFoodCategories(){
        return ResponseEntity.ok(foodsService.getAllFoodCategories());
    }

    @CrossOrigin
    @GetMapping(path = "testGetFoodDetail")
    public ResponseEntity<?> test(@RequestBody FoodDetailRequest foodDetailRequest){
        return ResponseEntity.ok(foodsService.foodDetail(foodDetailRequest));
    }



}
