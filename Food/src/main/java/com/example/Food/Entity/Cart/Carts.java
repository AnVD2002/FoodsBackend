package com.example.Food.Entity.Cart;

import com.example.Food.Entity.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cart")
public class Carts {
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartID;
    @Column(name = "user_id", insertable = false, updatable = false)
    private Integer userID;
    @Column(name = "create_at")
    private LocalDate createAt;
    @Column(name = "update_at")
    private LocalDate updateAt;
    @Column(name = "total")
    private Double total;
    @Column(name = "promote_id", insertable = false, updatable = false)
    private Integer promoteID;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonManagedReference("cart-user")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart")
    @JsonManagedReference("cart-cartItems")
    private List<CartItems> cartItems;

    @ManyToOne()
    @JsonBackReference("promote-carts")
    @JoinColumn(name = "promote_code")
    private Promote promote;






}
