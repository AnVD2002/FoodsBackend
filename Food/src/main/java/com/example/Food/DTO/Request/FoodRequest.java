package com.example.Food.DTO.Request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodRequest {
   private int foodCategoryID;
   private String foodName;
   private List<Integer> propertyID;
}
