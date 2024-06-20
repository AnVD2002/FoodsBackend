package com.example.Food.DTO.Response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllFoodDTO {
    private Integer foodID;
    private String foodName;
    private String description;
    private String image;
    private Integer foodCategoryID;
    private Double rating;
    private List<FoodDetailDTO> foodDetails;
}
