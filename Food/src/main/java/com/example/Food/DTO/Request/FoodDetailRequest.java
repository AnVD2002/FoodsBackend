package com.example.Food.DTO.Request;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodDetailRequest {
    private int foodID;
    private List<Integer> propertyDetails;
}
