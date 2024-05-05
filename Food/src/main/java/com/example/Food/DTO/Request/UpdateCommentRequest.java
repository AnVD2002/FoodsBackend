package com.example.Food.DTO.Request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCommentRequest {
    private int commentID;
    private String content;
}
