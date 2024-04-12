package com.example.Food.Entity.Order;

import com.example.Food.Entity.Food.FoodDetails;
import com.example.Food.Entity.Food.FoodDetailsPropertyDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "order_details")
@Builder
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private int orderDetailID;
    @Column(name = "order_id", insertable = false, updatable = false)
    private int orderID;
    @Column(name = "food_detail_id", insertable = false, updatable = false)
    private int foodDetailID;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "total")
    private double total;

    @ManyToOne
    @JsonBackReference("order-orderDetails")
    @JoinColumn(name = "order_id")
    private Orders order;

    @ManyToOne
    @JsonBackReference("foodDetail-orderDetails")
    @JoinColumn(name = "food_detail_id")
    private FoodDetails foodDetail;

}
