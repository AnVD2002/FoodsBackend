package com.example.Food.DTO.Response;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateFoodDetailResponse {
    private Integer foodDetailID;
    private String foodDetailName;
    private Integer quantity;
    private Double price;
    private Integer order;
    private String message;
}
