package com.example.Food.DTO.Response.Admin;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewUserResponse {
    private Integer userID;
    private String userName;
    private Integer decentralization;
    private Boolean status;
    private String mail;
    private String phone;
    private String img;
}
