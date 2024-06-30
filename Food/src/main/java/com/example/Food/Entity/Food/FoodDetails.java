package com.example.Food.Entity.Food;


import com.example.Food.Entity.Cart.CartItems;
import com.example.Food.Entity.Order.OrderDetails;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "food_details")
public class FoodDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_detail_id")
    private Integer foodDetailID;
    @Column(name = "food_detail_name")
    private String foodDetailName;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "price")
    private double price;
    @Column(name = "ordered")
    private Integer ordered;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "foodDetail")
    @JsonManagedReference("foodDetail-foodDetailsPropertyDetails")
    private List<FoodDetailsPropertyDetails> foodDetailsPropertyDetails;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "foodDetail")
    @JsonManagedReference("foodDetail-foodDetailsPropertyDetails")
    private List<OrderDetails> orderDetails;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "foodDetail")
    @JsonManagedReference("foodDetail-cartItem")
    private CartItems cartItem;

}
