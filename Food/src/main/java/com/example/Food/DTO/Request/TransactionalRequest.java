package com.example.Food.DTO.Request;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionalRequest {
    private Long amountParam;
}
