package com.example.Food.Service.Foods;

import com.example.Food.DTO.Request.ClientRequest.*;
import com.example.Food.DTO.Response.*;
import com.example.Food.DTO.Response.User.FoodDetailDTOa;
import com.example.Food.Entity.Food.*;
import com.example.Food.Entity.Food.Properties;
import com.example.Food.Repository.*;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    @Override
    public ResponseEntity<?> CreateFoodWithProperties(FoodRequest foodRequest) {
        if(foodRequest.getFoodName().isEmpty()){
            return new ResponseEntity<>("Name cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (foodRequest.getFoodCategoryID()==null){
            return new ResponseEntity<>("Category cannot be null", HttpStatus.BAD_REQUEST);
        }
        if(foodRequest.getImg().isEmpty()){
            return new ResponseEntity<>("Image cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if(foodRequest.getPropertyID().isEmpty()){
            return new ResponseEntity<>("Property cannot be empty", HttpStatus.BAD_REQUEST);
        }
        Optional<FoodCategories> foodCategory = foodCategoriesRepository.findById(foodRequest.getFoodCategoryID());
        List<String> propertyNameList = new ArrayList<>();
        if(foodCategory.isPresent()){
            Foods food = new Foods();
            food.setFoodName(foodRequest.getFoodName());
            food.setFoodCategory(foodCategory.get());
            food.setImage(foodRequest.getImg());
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
    public ResponseEntity<?> createFoodWithPropertiesDetails(FoodPropertyDetailsRequest request) {
        Optional<Foods> foodOpt = foodsRepository.findById(request.getFoodID());

        if (foodOpt.isEmpty()) {
            return new ResponseEntity<>("Không tồn tại món ăn có ID: " + request.getFoodID(), HttpStatus.BAD_REQUEST);
        }

        String foodPropertyName= GetName(request.getFoodID(), request.getPropertyDetailID());
        List<Integer> existingFoodDetailIDs = foodDetailsPropertyDetailsRepository.findFoodDetailIDsByFoodIDAndPropertyDetailIDs(request.getFoodID(), request.getPropertyDetailID());

        if (!existingFoodDetailIDs.isEmpty()) {
            FoodDetails existingFoodDetail = foodDetailsRepository.findById(existingFoodDetailIDs.get(0)).orElse(null);
            if (existingFoodDetail != null) {
                existingFoodDetail.setQuantity(existingFoodDetail.getQuantity() + request.getQuantity());
                existingFoodDetail.setPrice(request.getPrice());
                foodDetailsRepository.save(existingFoodDetail);
                return ResponseEntity.ok(FoodDetailDTOa.builder()
                        .foodDetailID(existingFoodDetail.getFoodDetailID())
                        .foodDetailName(existingFoodDetail.getFoodDetailName())
                        .ordered(existingFoodDetail.getOrdered())
                        .price(existingFoodDetail.getPrice())
                        .quantity(existingFoodDetail.getQuantity())
                        .message("Món ăn đã tồn tại và đã được cập nhật")
                        .build());
            }
        } else {
            FoodDetails newFoodDetail = new FoodDetails();
            newFoodDetail.setPrice(request.getPrice());
            newFoodDetail.setQuantity(request.getQuantity());
            newFoodDetail.setFoodDetailName(foodPropertyName);

            for (Integer propertyDetailID : request.getPropertyDetailID()) {
                Optional<PropertyDetails> propertyDetailOpt = propertyDetailsRepository.findById(propertyDetailID);
                propertyDetailOpt.ifPresent(propertyDetail -> {
                    FoodDetailsPropertyDetails foodDetailsPropertyDetail = new FoodDetailsPropertyDetails();
                    foodDetailsPropertyDetail.setPropertyDetail(propertyDetail);
                    foodDetailsPropertyDetail.setFood(foodOpt.get());
                    foodDetailsPropertyDetail.setFoodDetail(newFoodDetail);
                    foodDetailsPropertyDetailsRepository.save(foodDetailsPropertyDetail);
                });
            }
            foodDetailsRepository.save(newFoodDetail);
            return ResponseEntity.ok(FoodDetailDTOa.builder()
                    .foodDetailID(newFoodDetail.getFoodDetailID())
                    .foodDetailName(newFoodDetail.getFoodDetailName())
                    .ordered(0)
                    .price(newFoodDetail.getPrice())
                    .quantity(newFoodDetail.getQuantity())
                    .message("Món ăn đã được tạo mới thành công")
                    .build());
        }
        return new ResponseEntity<>("Đã xảy ra lỗi khi tạo mới món ăn", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Transactional
    @Override
    public ResponseEntity<?> deleteFood(Integer foodID) {
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
        return getAllFoodDTOS(foods);
    }

    @NotNull
    private List<AllFoodDTO> getAllFoodDTOS(List<Foods> foods) {
        List<AllFoodDTO> allFoodDTOs = new ArrayList<>();
        for (Foods food : foods) {
            Double rating = getRatingForFood(food.getFoodID());
            List<FoodDetailDTO> foodDetailDTOs = food.getFoodDetailsPropertyDetails().stream()
                    .map(fpdd -> new FoodDetailDTO(fpdd.getFoodDetail().getFoodDetailID(), fpdd.getFoodDetail().getPrice(), fpdd.getFoodDetail().getQuantity(), fpdd.getFoodDetail().getFoodDetailName(), fpdd.getFoodDetail().getOrdered())).distinct().collect(Collectors.toList());
            AllFoodDTO allFoodDTO = new AllFoodDTO(food.getFoodID(), food.getFoodName(), food.getDescription() , food.getImage() ,food.getFoodCategory().getFoodCategoryID(),rating, foodDetailDTOs);
            allFoodDTOs.add(allFoodDTO);
        }
        return allFoodDTOs;
    }

    private Double getRatingForFood(Integer foodID) {
        Double rating = commentRepository.findAverageRatingByFoodID(foodID);
        return (rating != null) ? rating : 0.0;
    }

    private AllFoodDTO getAllFoodDTO(Foods food) {
        Double rating = getRatingForFood(food.getFoodID());
        List<FoodDetailDTO> foodDetailDTOs = food.getFoodDetailsPropertyDetails().stream()
                .map(fpdd -> new FoodDetailDTO(
                        fpdd.getFoodDetail().getFoodDetailID(),
                        fpdd.getFoodDetail().getPrice(),
                        fpdd.getFoodDetail().getQuantity(),
                        fpdd.getFoodDetail().getFoodDetailName(),
                        fpdd.getFoodDetail().getOrdered()))
                .distinct()
                .collect(Collectors.toList());
        return new AllFoodDTO(
                food.getFoodID(),
                food.getFoodName(),
                food.getDescription(),
                food.getImage(),
                food.getFoodCategory().getFoodCategoryID(),
                rating,
                foodDetailDTOs
        );
    }

    @Override
    public List<FoodCategories> getAllFoodCategories() {
        return foodCategoriesRepository.findAll();
    }

    @Override
    public List<PropertyDetails> allPropertyDetails() {
        return propertyDetailsRepository.findAll();
    }

    @Override
    public List<PropertyDetails> getPropertyDetailByFoodID(Integer foodID) {
        return propertyDetailsRepository.getPropertyDetailsByFoodId(foodID);
    }

    @Override
    public ResponseEntity<?> getFoodDetailB(FoodDetailRequest foodDetailRequest) {
        Optional<Foods> food = foodsRepository.findById(foodDetailRequest.getFoodID());
        if (food.isEmpty()){
            return new ResponseEntity<>("not exist foodID "+ foodDetailRequest.getFoodID(), HttpStatus.NOT_FOUND);
        }
        List<Integer> foodDetailsIDs = foodDetailsPropertyDetailsRepository.findFoodDetailIDsByFoodIDAndPropertyDetailIDs(foodDetailRequest.getFoodID(), foodDetailRequest.getPropertyDetails());
        if(!foodDetailsIDs.isEmpty()){
            Optional<FoodDetails> foodDetail = foodDetailsRepository.findById(foodDetailsIDs.get(0));
            if(foodDetail.isPresent()){
                FoodDetailResponse foodDetailResponse = FoodDetailResponse.builder()
                        .foodDetailName(foodDetail.get().getFoodDetailName())
                        .price(foodDetail.get().getPrice())
                        .foodDetailID(foodDetail.get().getFoodDetailID())
                        .description(food.get().getDescription())
                        .quantity(foodDetail.get().getQuantity())
                        .img(food.get().getImage())
                        .build();
                return new ResponseEntity<>(foodDetailResponse,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("not exist ", HttpStatus.NOT_FOUND);

    }

    @Override
    public List<AllFoodDTO> getFoodDetailsFilter(FoodFilterRequest foodFilterRequest) {
        Specification<Foods> spec = Specification.where(null);
        if (foodFilterRequest.getCategoryID() != null) {
            spec = spec.and(FoodSpecification.hasCategory(foodFilterRequest.getCategoryID()));
        }
        if (foodFilterRequest.getRating() != null) {
            spec = spec.and(FoodSpecification.hasAverageRating(foodFilterRequest.getRating()));
        }
        if (foodFilterRequest.getName() != null) {
            spec = spec.and(FoodSpecification.hasNameLike(foodFilterRequest.getName()));
        }

        int page = (foodFilterRequest.getPage() != null) ? foodFilterRequest.getPage() : 1;
        int pageSize = (foodFilterRequest.getPageSize() != null) ? foodFilterRequest.getPageSize() : 10;


        Pageable pageable = PageRequest.of(
                page - 1,
                pageSize
        );
        spec = spec.and(FoodSpecification.hasPagination(pageable));
        Page<Foods> foodsPage = foodsRepository.findAll(spec, pageable);
        List<Foods> foods = foodsPage.getContent();
        return getAllFoodDTOS(foods);
    }

    @Override
    public List<AllFoodDTO> getFoodDetailsFilterClient(FoodDetailClientRequest request) {
        Specification<Foods> spec = Specification.where(null);

        if (request.getCategoryID() != null) {
            spec = spec.and(FoodSpecification.hasCategory(request.getCategoryID()));
        }
        List<Foods> foods = foodsRepository.findAll(spec);
        List<AllFoodDTO> filterFoods = getAllFoodDTOS(foods);


        List<AllFoodDTO> filteredFoods = filterFoods.stream()
                .filter(filter -> request.getRating() == null || filter.getRating() >= request.getRating())
                .filter(product -> {
                    Double minPrice = product.getFoodDetails().stream()
                            .min(Comparator.comparingDouble(FoodDetailDTO::getPrice))
                            .map(FoodDetailDTO::getPrice)
                            .orElse(Double.MAX_VALUE);
                    return (request.getStartPrice() == null || minPrice >= request.getStartPrice()) &&
                            (request.getEndPrice() == null || minPrice <= request.getEndPrice());
                })
                .collect(Collectors.toList());

        switch (request.getSelectID()) {
            case 1:
                filteredFoods.sort(Comparator.comparing(AllFoodDTO::getFoodName));
                break;
            case 2:
                filteredFoods.sort(Comparator.comparingDouble(this::getLowestPrice));
                break;
            case 3:
                filteredFoods.sort(Comparator.comparingDouble(this::getLowestPrice).reversed());
                break;
            case 4:
                filteredFoods.sort(Comparator.comparingInt(this::getTotalOrdered).reversed());
                break;
            case 0:
            default:
                break;
        }
        return filteredFoods;
    }

    @Override
    public ResponseEntity<AllFoodDTO> getFoodDetailByID(Integer foodID) {
        Optional<Foods> food = foodsRepository.findById(foodID);
        if (food.isPresent()) {
            AllFoodDTO allFoodDTO = getAllFoodDTO(food.get());
            return ResponseEntity.ok(allFoodDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    private double getLowestPrice(AllFoodDTO foodDTO) {
        return foodDTO.getFoodDetails().stream()
                .mapToDouble(FoodDetailDTO::getPrice)
                .min()
                .orElse(Double.MAX_VALUE);
    }
    private int getTotalOrdered(AllFoodDTO foodDTO) {
        return foodDTO.getFoodDetails().stream()
                .mapToInt(FoodDetailDTO::getOrdered)
                .sum();
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
    @Override
    public ResponseEntity<?> updateFoodDetail(UpdateFoodDetailRequest request){
        UpdateFoodDetailResponse response;
        if(request.getFoodDetailID().describeConstable().isEmpty()){
             response= UpdateFoodDetailResponse.builder()
                                                .message("not exist foodDetailID")
                                                .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Optional<FoodDetails> foodDetailOtp = foodDetailsRepository.findById(request.getFoodDetailID());
        if(foodDetailOtp.isEmpty()){
            response = UpdateFoodDetailResponse.builder()
                    .message("not exist foodDetail")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        else {
            FoodDetails foodDetail = foodDetailOtp.get();
            foodDetail.setPrice(request.getPrice());
            foodDetail.setQuantity(request.getQuantity());
            foodDetailsRepository.save(foodDetail);

            response= UpdateFoodDetailResponse.builder()
                    .foodDetailName(foodDetail.getFoodDetailName())
                    .foodDetailID(foodDetail.getFoodDetailID())
                    .order(foodDetail.getOrdered())
                    .price(foodDetail.getPrice())
                    .quantity(request.getQuantity())
                    .message("updated")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> deleteFoodDetail(Integer foodDetailID) {
        Optional<FoodDetails> foodDetail = foodDetailsRepository.findById(foodDetailID);
        if(foodDetail.isEmpty()){
            return new ResponseEntity<>("not exist foodDetailID", HttpStatus.BAD_REQUEST);
        }
        foodDetailsPropertyDetailsRepository.findFoodDetailID(foodDetailID);
        foodDetailsRepository.delete(foodDetail.get());
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }

}
