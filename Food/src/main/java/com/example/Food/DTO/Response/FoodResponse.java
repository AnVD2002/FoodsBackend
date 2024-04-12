package com.example.Food.DTO.Response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodResponse {
    private int foodID;
    private String foodName;
    private String foodCategoryName;
    private List<String> propertyNameList;
}
