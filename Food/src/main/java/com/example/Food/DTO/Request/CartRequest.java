package com.example.Food.DTO.Request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartRequest {
    private int foodID;
    private List<Integer> propertyDetailID;
    private int userID;
    private int quantity;
}
