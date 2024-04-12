package com.example.Food.DTO.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDetailDTO {
    private int foodDetailId;
    private double price;
    private int quantity;
}
