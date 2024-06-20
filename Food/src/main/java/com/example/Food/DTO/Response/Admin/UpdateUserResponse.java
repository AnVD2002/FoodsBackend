package com.example.Food.DTO.Response.Admin;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserResponse {
    private String userName;
    private boolean status;
    private String numberPhone;
    private LocalDate updateDate;
    private String mail;
    private String img;
}
