package com.example.Food.DTO.Request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepCommentRequest {
    private int userID;
    private int foodID;
    private int parentID;
    private LocalDate createAt;
    private String content;
}
