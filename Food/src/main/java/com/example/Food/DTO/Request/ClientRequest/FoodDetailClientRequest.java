package com.example.Food.DTO.Request.ClientRequest;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodDetailClientRequest {
    Integer SelectID;
    Integer categoryID;
    Double rating;
    Double startPrice;
    Double endPrice;
}
