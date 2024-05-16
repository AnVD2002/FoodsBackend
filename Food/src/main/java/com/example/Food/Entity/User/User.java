package com.example.Food.Entity.User;


import com.example.Food.Entity.Cart.Carts;
import com.example.Food.Entity.Comment;
import com.example.Food.Entity.Order.Orders;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "user")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Integer UserID;
    @Column(name = "username")
    private String userName;
    @Column(name = "email")
    private String Email;
    @Column(name = "date_update")
    private LocalDate updateDate;
    @Column(name = "password")
    private String password;
    @Column(name = "decentralization_id",insertable = false,updatable = false)
    private Integer decentralizationID;
    @Column(name = "update_password_token")
    private String updatePasswordToken;
    @Column(name = "status")
    private boolean isConfirmed;
    @Column(name = "update_password_token_expiry")
    private LocalDate updatePasswordTokenExpiry;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "decentralization_id")
    private Decentralization decentralization;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference("user-orders")
    private List<Orders> orders;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference("user-comments")
    private List<Comment> comments;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonBackReference("cart-user")
    private Carts cart;
}
