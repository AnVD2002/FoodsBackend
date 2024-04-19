package com.example.Food.DTO.Request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodPropertyDetailsRequest {
    private int foodID;
    private int quantity;
    private double price;
    List<Integer> propertyDetailID;
}
