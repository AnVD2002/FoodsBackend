package com.example.Food.Controller;

import com.example.Food.DTO.Request.FoodPropertyDetailsRequest;
import com.example.Food.DTO.Request.FoodRequest;
import com.example.Food.DTO.Request.FoodUpdateRequest;
import com.example.Food.Service.Foods.FoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/food/")
@RestController
public class FoodController {
    @Autowired
    private FoodsService foodsService;
    @CrossOrigin
    @PostMapping(path = "createFoodProperty",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> CreateFoodProperty(@RequestBody FoodRequest request){
        return ResponseEntity.ok(foodsService.CreateFoodWithProperties(request));
    }
    @CrossOrigin
    @PostMapping(path = "createFoodPropertyDetails",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> CreateFoodPropertyDetails(@RequestBody FoodPropertyDetailsRequest request){
        return ResponseEntity.ok(foodsService.createFoodWithPropertiesDetails(request));
    }
    @DeleteMapping(path = "deleteFood", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> DeleteFood(@RequestParam int foodID){
        return ResponseEntity.ok(foodsService.deleteFood(foodID));
    }
    @PutMapping(path = "updateFood", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateFood(@RequestParam FoodUpdateRequest foodUpdateRequest){
        return ResponseEntity.ok(foodUpdateRequest);
    }
    @GetMapping(path = "allFood")
    public ResponseEntity<?> allFood(){
        return ResponseEntity.ok(foodsService.getAllFoods());
    }
}
