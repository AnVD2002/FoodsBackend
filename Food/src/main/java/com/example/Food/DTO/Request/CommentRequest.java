package com.example.Food.DTO.Request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequest {
    private Integer userID;
    private Integer foodID;
    private LocalDate createAt;
    private String content;
    private Integer rating;
}
