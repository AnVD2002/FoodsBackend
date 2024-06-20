package com.example.Food.DTO.Response.User;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
    private Integer userID;
    private String userName;
    private Integer foodID;
    private Integer parentID;
    private String content;
    private Integer commentID;
    private LocalDate createAt;
    private Integer rating;
}
