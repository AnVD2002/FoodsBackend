package com.example.Food.DTO.Response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllFoodDTO {
    private int foodID;
    private String foodName;
    private String description;
    private String image;
    private int foodCategoryID;
    private List<FoodDetailDTO> foodDetails;
}
