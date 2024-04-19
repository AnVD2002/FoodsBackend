package com.example.Food.Service.Foods;

import com.example.Food.DTO.Request.FoodPropertyDetailsRequest;
import com.example.Food.DTO.Request.FoodRequest;
import com.example.Food.DTO.Request.FoodUpdateRequest;
import com.example.Food.DTO.Response.AllFoodDTO;
import com.example.Food.DTO.Response.FoodDetailDTO;
import com.example.Food.DTO.Response.FoodResponse;
import com.example.Food.Entity.Food.*;
import com.example.Food.Entity.Food.Properties;
import com.example.Food.Repository.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FoodsService implements ImplFoodsService{
    @Autowired
    private PropertiesRepository propertiesRepository;
    @Autowired
    private FoodPropertiesRepository foodPropertiesRepository;
    @Autowired
    private FoodsRepository foodsRepository;
    @Autowired
    private PropertyDetailsRepository propertyDetailsRepository;
    @Autowired
    private FoodDetailsPropertyDetailsRepository foodDetailsPropertyDetailsRepository;
    @Autowired
    private FoodDetailsRepository foodDetailsRepository;
    @Autowired
    private FoodCategoriesRepository foodCategoriesRepository;
    @Transactional
    @Override
    public ResponseEntity<?> CreateFoodWithProperties(FoodRequest foodRequest) {
        Optional<FoodCategories> foodCategory = foodCategoriesRepository.findById(foodRequest.getFoodCategoryID());
        List<String> propertyNameList = new ArrayList<>();
        if(foodCategory.isPresent()){
            Foods food = new Foods();
            food.setFoodName(foodRequest.getFoodName());
            food.setFoodCategory(foodCategory.get());
            food=foodsRepository.save(food);
            List<FoodProperties> listFoodProperties = new ArrayList<>();
            for(int i=0 ; i<foodRequest.getPropertyID().size();i++){
                FoodProperties foodProperties = new FoodProperties();
                Optional<Properties> property = propertiesRepository.findById(foodRequest.getPropertyID().get(i));
                if(property.isEmpty()){
                    return new ResponseEntity<>("does not exist property", HttpStatus.NOT_FOUND);
                }
                foodProperties.setFood(food);
                foodProperties.setProperty(property.get());
                listFoodProperties.add(foodProperties);
                propertyNameList.add(property.get().getPropertyName());
            }
            foodPropertiesRepository.saveAll(listFoodProperties);

            FoodResponse foodResponse= FoodResponse.builder().foodID(food.getFoodID())
                    .foodName(food.getFoodName())
                    .foodCategoryName(foodCategory.get().getFoodCategoryName()
                    ).propertyNameList(propertyNameList).build();
            return new ResponseEntity<>(foodResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>("does not exist foodType", HttpStatus.NOT_FOUND);
    }
    @Transactional
    @Override
    public ResponseEntity<?> createFoodWithPropertiesDetails(FoodPropertyDetailsRequest foodPropertyDetailsRequest) {
        Optional<Foods> foodOpt = foodsRepository.findById(foodPropertyDetailsRequest.getFoodID());
        int propertyDetailSize = foodPropertyDetailsRequest.getPropertyDetailID().size();

        String foodPropertyName= GetName(foodPropertyDetailsRequest.getFoodID(), foodPropertyDetailsRequest.getPropertyDetailID());
        
        List<Integer> propertyList = propertyDetailsRepository.findPropertyDetailsByFoodIdAndPropertyDetailIds(foodPropertyDetailsRequest.getFoodID(), foodPropertyDetailsRequest.getPropertyDetailID());

        List<Integer> propertyDetailIdsNotFound = new ArrayList<>();

        List<FoodDetailsPropertyDetails> foodDetailsPropertyDetailsList = new ArrayList<>();

        List<Integer> foodDetailIDs = foodDetailsPropertyDetailsRepository.findFoodDetailIDsByFoodIDAndPropertyDetailIDs(foodPropertyDetailsRequest.getFoodID(), foodPropertyDetailsRequest.getPropertyDetailID());

        Set<Integer> num = new HashSet<>(foodDetailIDs);
        FoodDetails foodDetail;
        foodDetail = new FoodDetails();
        foodDetail.setPrice(foodPropertyDetailsRequest.getPrice());
        foodDetail.setQuantity(foodPropertyDetailsRequest.getQuantity());
        foodDetail.setFoodDetailName(foodPropertyName);
        if(foodOpt.isPresent()){
            if(num.size()==1&& foodDetailIDs.size()==foodPropertyDetailsRequest.getPropertyDetailID().size()){
                return new ResponseEntity<>("already exist",HttpStatus.NOT_FOUND);
            }
            if(propertyList.size()!=foodPropertyDetailsRequest.getPropertyDetailID().size()){
                for(int i= 0 ; i<propertyDetailSize;i++){
                    Optional<PropertyDetails> propertyDetail = propertyDetailsRepository.findById(foodPropertyDetailsRequest.getPropertyDetailID().get(i));
                    if(propertyDetail.isEmpty()){
                        propertyDetailIdsNotFound.add(foodPropertyDetailsRequest.getPropertyDetailID().get(i));
                        continue;
                    }
                }
            }
            for(int i= 0 ; i<propertyDetailSize;i++){
                Optional<PropertyDetails> propertyDetail = propertyDetailsRepository.findById(foodPropertyDetailsRequest.getPropertyDetailID().get(i));
                if(propertyDetail.isEmpty()){
                    propertyDetailIdsNotFound.add(foodPropertyDetailsRequest.getPropertyDetailID().get(i));
                    continue;
                }
                else {
                    FoodDetailsPropertyDetails foodDetailsPropertyDetail = new FoodDetailsPropertyDetails();
                    foodDetailsPropertyDetail.setPropertyDetail(propertyDetail.get());
                    foodDetailsPropertyDetail.setFood(foodOpt.get());
                    foodDetailsPropertyDetail.setFoodDetail(foodDetail);
                    foodDetailsPropertyDetailsList.add(foodDetailsPropertyDetail);
                }
            }
        }
        else{
           return new ResponseEntity<>("not exist food" + foodPropertyDetailsRequest.getFoodID(), HttpStatus.OK);
        }
        if (!propertyDetailIdsNotFound.isEmpty()) {
            return new ResponseEntity<>("Property details not found for IDs: " + propertyDetailIdsNotFound, HttpStatus.NOT_FOUND);
        }
        foodDetailsRepository.save(foodDetail);
        foodDetailsPropertyDetailsRepository.saveAll(foodDetailsPropertyDetailsList);
        return ResponseEntity.ok("Food with properties details created successfully.");
    }
    @Transactional
    @Override
    public ResponseEntity<?> deleteFood(int foodID) {
        List<Integer> foodDetailIDs=  foodDetailsPropertyDetailsRepository.findDistinctFoodDetailIDsByFoodID(foodID);
        Optional<Foods> food = foodsRepository.findById(foodID);
        if(food.isEmpty()){
            return new ResponseEntity<>("not exist foodID "+ foodID, HttpStatus.NOT_FOUND );
        }
        foodsRepository.delete(food.get());
        for(Integer foodDetailID : foodDetailIDs) {
            Optional<FoodDetails> foodDetail = foodDetailsRepository.findById(foodDetailID);
            foodDetail.ifPresent(foodDetails -> foodDetailsRepository.delete(foodDetails));
        }
        return new ResponseEntity<>("deleted foodID" + foodID, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateFood(FoodUpdateRequest foodUpdateRequest) {
        Optional<Foods> food = foodsRepository.findById(foodUpdateRequest.getFoodID());
        List<Integer> foodDetailsPropertyDetailID = foodDetailsPropertyDetailsRepository.findIDByFoodIDAndPropertyDetailIDs(foodUpdateRequest.getFoodID(), foodUpdateRequest.getPropertyDetailID());
        List<Integer> foodDetailIDs = foodDetailsPropertyDetailsRepository.findFoodDetailIDsByFoodIDAndPropertyDetailIDs(foodUpdateRequest.getFoodID(), foodUpdateRequest.getPropertyDetailID());
        List<FoodDetailsPropertyDetails> foodDetailsPropertyDetailsList = new ArrayList<>();
        Set<Integer> num = new HashSet<>(foodDetailIDs);
        if(food.isEmpty()){
            return new ResponseEntity<>("not exist food" , HttpStatus.NOT_FOUND);
        }
        else {
            if(num.size()!=1 && foodUpdateRequest.getPropertyDetailID().size()==foodDetailsPropertyDetailID.size()){
                food.get().setFoodName(foodUpdateRequest.getFoodName());
                food.get().setImage(foodUpdateRequest.getImage());
                for (Integer integer : foodDetailsPropertyDetailID) {
                    Optional<FoodDetailsPropertyDetails> foodDetailsPropertyDetail = foodDetailsPropertyDetailsRepository.findById(integer);
                    if(foodDetailsPropertyDetail.isPresent()){
                        foodDetailsPropertyDetail.get().getFoodDetail().setQuantity(foodUpdateRequest.getQuantity());
                        foodDetailsPropertyDetail.get().getFoodDetail().setPrice(foodUpdateRequest.getPrice());
                        foodDetailsPropertyDetailsList.add(foodDetailsPropertyDetail.get());
                    }
                }
                foodDetailsPropertyDetailsRepository.saveAll(foodDetailsPropertyDetailsList);
                food.get().setFoodDetailsPropertyDetails(foodDetailsPropertyDetailsList);
                foodsRepository.save(food.get());
            }
            return new ResponseEntity<>("Some Properties not created", HttpStatus.NOT_FOUND);
        }

    }
    @Override
    public List<AllFoodDTO> getAllFoods() {
        List<Foods> foods = foodsRepository.findAll();
        List<AllFoodDTO> allFoodDTOs = new ArrayList<>();
        for (Foods food : foods) {
            Set<FoodDetailDTO> foodDetailDTOsSet = food.getFoodDetailsPropertyDetails().stream()
                    .map(fpdd -> new FoodDetailDTO(fpdd.getFoodDetail().getFoodDetailID(), fpdd.getFoodDetail().getPrice(), fpdd.getFoodDetail().getQuantity(),fpdd.getFoodDetail().getFoodDetailName()))
                    .collect(Collectors.toSet());
            List<FoodDetailDTO> foodDetailDTOs = new ArrayList<>(foodDetailDTOsSet);
            AllFoodDTO allFoodDTO = new AllFoodDTO(food.getFoodID(), food.getFoodName(), food.getDescription() , food.getImage() ,food.getFoodCategory().getFoodCategoryID(), foodDetailDTOs);
            allFoodDTOs.add(allFoodDTO);
        }
        return allFoodDTOs;
    }
    @Override
    public List<FoodCategories> getAllFoodCategories() {
        return foodCategoriesRepository.findAll();
    }

    @Override
    public List<PropertyDetails> allPropertyDetails() {
        return propertyDetailsRepository.findAll();
    }

    public String GetName(int foodID, List<Integer> propertyDetailIDs) {
        Optional<Foods> food = foodsRepository.findById(foodID);
        StringBuilder foodDetailsName = new StringBuilder();
        String foodName = null;
        if (food.isPresent()) {
            foodName = food.get().getFoodName();
        }
        for (Integer propertyDetailID : propertyDetailIDs) {
            Optional<PropertyDetails> propertyDetail = propertyDetailsRepository.findById(propertyDetailID);
            propertyDetail.ifPresent(propertyDetails -> foodDetailsName
                    .append(", ")
                    .append(propertyDetails.getPropertyDetailName()));
        }
        return foodName + foodDetailsName.toString();
    }


}
