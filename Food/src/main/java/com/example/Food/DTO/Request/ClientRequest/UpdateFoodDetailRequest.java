package com.example.Food.DTO.Request.ClientRequest;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateFoodDetailRequest {
    private Integer foodDetailID;
    private Integer quantity;
    private Double price;
}
