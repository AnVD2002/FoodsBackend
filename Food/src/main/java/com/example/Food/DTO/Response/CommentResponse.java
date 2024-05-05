package com.example.Food.DTO.Response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
    private int userID;
    private String userName;
    private int foodID;
    private int parentID;
    private String content;
    private int commentID;
    private LocalDate createAt;
}
