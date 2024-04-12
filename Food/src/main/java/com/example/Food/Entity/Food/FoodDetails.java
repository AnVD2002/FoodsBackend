package com.example.Food.Entity.Food;


import com.example.Food.Entity.Order.OrderDetails;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private int foodDetailID;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price")
    private double price;

    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "foodDetail")
    @JsonManagedReference("foodDetail-orderDetails")
    private List<OrderDetails> orderDetails;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "foodDetail",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("foodDetail-foodDetailsPropertyDetails")
    private List<FoodDetailsPropertyDetails> foodDetailsPropertyDetails;
}