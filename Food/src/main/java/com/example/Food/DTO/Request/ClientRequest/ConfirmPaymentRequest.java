package com.example.Food.DTO.Request.ClientRequest;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmPaymentRequest {
    private Integer orderID;
    private boolean isPaid;
}
