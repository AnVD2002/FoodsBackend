package com.example.Food.DTO.Request.ClientRequest;

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
    private String userName;
    private Integer quantity;
}
