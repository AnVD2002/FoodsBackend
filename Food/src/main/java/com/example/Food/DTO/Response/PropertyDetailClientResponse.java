package com.example.Food.DTO.Response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyDetailClientResponse {
    private Integer propertyDetailID;
    private String propertyDetailName;
    private Integer propertyID;
    private String propertyName;
}
