package com.example.Food.DTO.Request.ClientRequest;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CancelRequest {
    private Integer orderDetailID;
    private String reason;
}
