package com.example.Food.DTO.Request;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyRequest {
    private Integer userID;
    private String name;
    private String address;
    private String phone;
    private Integer paymentMethodID;
}
