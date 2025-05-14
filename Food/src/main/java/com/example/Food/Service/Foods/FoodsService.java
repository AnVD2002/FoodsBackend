package com.example.Food.Service.Foods;

import com.example.Food.DTO.Request.ClientRequest.*;
import com.example.Food.DTO.Response.AllFoodDTO;
import com.example.Food.Entity.Food.FoodCategories;
import com.example.Food.Entity.Food.PropertyDetails;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FoodsService {
    public ResponseEntity<?> CreateFoodWithProperties(FoodRequest foodRequest);
    public ResponseEntity<?> createFoodWithPropertiesDetails(FoodPropertyDetailsRequest foodPropertyDetailsRequest);
    public ResponseEntity<?> deleteFood(Integer foodID);
    public ResponseEntity<?> updateFood(FoodUpdateRequest foodUpdateRequest);
    public List<AllFoodDTO> getAllFoods();
    public List<FoodCategories> getAllFoodCategories();
    public List<PropertyDetails> allPropertyDetails();
    public ResponseEntity<?> getPropertyDetailByFoodID(Integer foodID);
    public ResponseEntity<?> getFoodDetailB(FoodDetailRequest foodDetailRequest);
    public List<AllFoodDTO> getFoodDetailsFilter(FoodFilterRequest foodFilterRequest);
    public List<AllFoodDTO> getFoodDetailsFilterClient(FoodDetailClientRequest foodDetailClientRequest);
    public ResponseEntity<AllFoodDTO> getFoodDetailByID(Integer foodID);
    public ResponseEntity<?> updateFoodDetail(UpdateFoodDetailRequest request);
    public ResponseEntity<?> deleteFoodDetail(Integer foodDetailID);
    public ResponseEntity<?> getAllProperties();
    public ResponseEntity<?> getAllCategory();
    public ResponseEntity<?> getTopOrder();
    public ResponseEntity<?> updateFood(UpdateFoods request);


}
