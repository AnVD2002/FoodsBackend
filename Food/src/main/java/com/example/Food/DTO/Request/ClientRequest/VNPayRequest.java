package com.example.Food.DTO.Request.ClientRequest;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VNPayRequest {
    private Integer orderID;
    private String code;


}
