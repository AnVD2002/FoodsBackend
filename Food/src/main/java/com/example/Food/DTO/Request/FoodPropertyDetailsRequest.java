package com.example.Food.DTO.Request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodPropertyDetailsRequest {
    private Integer foodID;
    private Integer quantity;
    private double price;
    List<Integer> propertyDetailID;
}
