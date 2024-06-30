package com.example.Food.DTO.Response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailAdminResponse {
    private Integer orderDetailID;
    private Integer userID;
    private Integer orderID;
    private String name;
    private Double price;
    private Integer quantity;
    private String img;
    private boolean isOrdered;
    private boolean isPaid;
    private boolean isCancelled;
    private String reason;
}
