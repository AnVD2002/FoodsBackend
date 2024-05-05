package com.example.Food.DTO.Request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequest {
    private int userID;
    private int foodID;
    private LocalDate createAt;
    private String content;
}
