package com.example.Food.Entity.Cart;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cart_items")
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Integer cartItemID;
    @Column(name = "cart_id" , insertable = false, updatable = false)
    private Integer cartID;
    @Column(name = "food_detail_id")
    private Integer foodDetailID;
    @Column(name = "food_detail_name")
    private String foodDetailName;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "price")
    private double price;
    @Column(name = "img")
    private String img;
    @Column(name = "description")
    private String description;

    @ManyToOne()
    @JsonBackReference("cart-cartItems")
    @JoinColumn(name = "cart_id")
    private Carts cart;

}
