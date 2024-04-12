package com.example.Food.Service.Foods;

import com.example.Food.DTO.Request.FoodPropertyDetailsRequest;
import com.example.Food.DTO.Request.FoodRequest;
import com.example.Food.DTO.Request.FoodUpdateRequest;
import com.example.Food.DTO.Response.AllFoodDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ImplFoodsService {
    public ResponseEntity<?> CreateFoodWithProperties(FoodRequest foodRequest);
    public ResponseEntity<?> createFoodWithPropertiesDetails(FoodPropertyDetailsRequest foodPropertyDetailsRequest);
    public ResponseEntity<?> deleteFood(int foodID);
    public ResponseEntity<?> updateFood(FoodUpdateRequest foodUpdateRequest);
    public List<AllFoodDTO> getAllFoods();
}
