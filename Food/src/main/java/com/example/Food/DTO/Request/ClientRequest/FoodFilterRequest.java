package com.example.Food.DTO.Request.ClientRequest;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodFilterRequest {
    private Integer categoryID;
    private Double rating;
    private String name;
    private Integer page;
    private Integer pageSize;
}
