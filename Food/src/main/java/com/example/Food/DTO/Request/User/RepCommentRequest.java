package com.example.Food.DTO.Request.User;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepCommentRequest {
    private String userName;
    private Integer foodID;
    private Integer parentID;
    private LocalDate createAt;
    private String content;
}
