package com.example.Food.DTO.Request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartRequest {
    private Integer foodID;
    private List<Integer> propertyDetailID;
    private Integer userID;
    private Integer quantity;
}
