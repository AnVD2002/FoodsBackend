package com.example.Food.DTO.Response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllFoodDTO {
    private int foodId;
    private String foodName;
    private List<FoodDetailDTO> foodDetails;
}
