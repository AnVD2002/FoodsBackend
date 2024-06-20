package com.example.Food.DTO.Request.User;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequest {
    private String userName;
    private Integer foodID;
    private LocalDate createAt;
    private String content;
    private Integer rating;
}
