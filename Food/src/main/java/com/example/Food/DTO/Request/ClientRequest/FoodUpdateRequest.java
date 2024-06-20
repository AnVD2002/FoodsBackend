package com.example.Food.DTO.Request.ClientRequest;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodUpdateRequest {
    private String foodName;
    private Integer foodID;
    private Integer quantity;
    private double price;
    private String image;
    List<Integer> propertyDetailID;
}
