package com.example.Food.DTO.Request.ClientRequest;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateFoods {
    Integer FoodID;
    String FoodName;
    String FoodDescription;
    String FoodImage;
}
