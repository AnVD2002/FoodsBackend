package com.example.Food.DTO.Request.ClientRequest;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionalRequest {
    private Long amountParam;
}
