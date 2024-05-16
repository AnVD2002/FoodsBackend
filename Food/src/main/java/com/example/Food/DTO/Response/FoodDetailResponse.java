package com.example.Food.DTO.Response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodDetailResponse {
    private Integer foodDetailID;
    private String foodDetailName;
    private String description;
    private double price;
    private Integer quantity;
    private String img;
}
