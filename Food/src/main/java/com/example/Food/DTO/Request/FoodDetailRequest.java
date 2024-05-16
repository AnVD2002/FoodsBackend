package com.example.Food.DTO.Request;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodDetailRequest {
    private Integer foodID;
    private List<Integer> propertyDetails;
}
