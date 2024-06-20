package com.example.Food.DTO.Response;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDetailDTO {
    private Integer foodDetailID;
    private double price;
    private Integer quantity;
    private String foodDetailName;
    private Integer ordered;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodDetailDTO that = (FoodDetailDTO) o;
        return Objects.equals(foodDetailID, that.foodDetailID) &&
                Objects.equals(price, that.price) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(foodDetailName, that.foodDetailName)&&
                Objects.equals(ordered, that.ordered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodDetailID, price, quantity, foodDetailName, ordered);
    }
}
