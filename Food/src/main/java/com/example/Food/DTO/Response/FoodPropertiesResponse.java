package com.example.Food.DTO.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodPropertiesResponse {
    private int propertyID;
    private String propertyName;
}
