package com.example.Food.DTO.Response.Admin;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderAdminResponse {
    private Integer orderID;
    private String userName;
    private String name;
    private String address;
    private String phone;
    private Double total;
    private Boolean isOrdered;
    private Boolean isPaid;
    private LocalDate localDate;
}
