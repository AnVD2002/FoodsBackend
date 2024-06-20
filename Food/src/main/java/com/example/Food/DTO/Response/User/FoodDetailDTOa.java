package com.example.Food.DTO.Response.User;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDetailDTOa {
    private Integer foodDetailID;
    private double price;
    private Integer quantity;
    private String foodDetailName;
    private Integer ordered;
    private String message;
}