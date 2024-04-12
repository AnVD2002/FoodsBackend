package com.example.Food.DTO.Response;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDetailDTO {
    private int foodDetailId;
    private double price;
    private int quantity;
    private String foodDetailName;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodDetailDTO that = (FoodDetailDTO) o;
        return Objects.equals(foodDetailId, that.foodDetailId) &&
                Objects.equals(price, that.price) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(foodDetailName, that.foodDetailName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodDetailId, price, quantity, foodDetailName);
    }
}
