package com.example.Food.DTO.Request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepCommentRequest {
    private Integer userID;
    private Integer foodID;
    private Integer parentID;
    private LocalDate createAt;
    private String content;
}
