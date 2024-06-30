package com.example.Food.DTO.Request.ClientRequest;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePropertyDetailRequest {
    private Integer propertyDetailID;
    private String propertyDetailName;
}
