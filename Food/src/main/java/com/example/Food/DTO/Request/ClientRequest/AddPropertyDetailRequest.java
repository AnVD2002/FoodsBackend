package com.example.Food.DTO.Request.ClientRequest;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddPropertyDetailRequest {
    private Integer propertyID;
    private String propertyDetailName;
}
