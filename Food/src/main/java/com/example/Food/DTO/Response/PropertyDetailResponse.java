package com.example.Food.DTO.Response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyDetailResponse {
    private String propertyDetailName;
    private Integer propertyDetailID;
}
