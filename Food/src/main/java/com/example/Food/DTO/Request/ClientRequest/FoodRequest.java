package com.example.Food.DTO.Request.ClientRequest;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodRequest {
   private Integer foodCategoryID;
   private String foodName;
   private List<Integer> propertyID;
   private String img;
   private String description;
}
