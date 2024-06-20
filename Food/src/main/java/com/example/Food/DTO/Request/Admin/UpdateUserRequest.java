package com.example.Food.DTO.Request.Admin;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
    private Integer userID;
    private String userName;
    private boolean status;
    private String numberPhone;
    private LocalDate updateDate;
    private String mail;
    private String img;
}
