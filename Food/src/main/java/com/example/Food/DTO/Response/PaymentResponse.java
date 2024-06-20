package com.example.Food.DTO.Response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private Integer orderID;
    private Integer paymentID;
    private boolean paymentStatus;
    private LocalDate paymentDate;
    private Double paymentAmount;
}
