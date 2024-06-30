package com.example.Food.DTO.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    private Integer categoryID;
    private String categoryName;
}
