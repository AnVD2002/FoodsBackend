package com.example.Food.DTO.Request;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodUpdateRequest {
    private String foodName;
    private int foodID;
    private int quantity;
    private double price;
    private String image;
    List<Integer> propertyDetailID;
}
