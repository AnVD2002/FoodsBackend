package com.example.Food.DTO.Response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodDetailResponse {
    private int foodDetailID;
    private String foodDetailName;
    private String description;
    private double price;
    private int quantity;
    private String img;
}
