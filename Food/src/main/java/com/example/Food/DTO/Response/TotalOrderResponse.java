package com.example.Food.DTO.Response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TotalOrderResponse {
    private Double total;
    private LocalDate orderDate;
    private String FoodName;
    private Integer FoodID;
}
