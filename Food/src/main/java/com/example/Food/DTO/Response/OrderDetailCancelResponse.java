package com.example.Food.DTO.Response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailCancelResponse {
    private Integer orderDetailID;
    private Integer cancelID;
    private String name;
    private Double price;
    private Integer quantity;
    private String img;
    private boolean isOrdered;
    private boolean isPaid;
    private String reason;
}
