package com.example.Food.Entity.Order;

import com.example.Food.Entity.Payment.Payment;
import com.example.Food.Entity.User.User;
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
@Table(name = "orders")
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderID;
    @Column(name = "user_id", insertable = false, updatable = false)
    private Integer userID;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "order_date")
    private LocalDate orderDate;
    @Column(name = "number_phone")
    private String numberPhone;
    @Column(name = "status")
    private boolean status;
    @Column(name = "total")
    private Double total;

    @ManyToOne()
    @JsonBackReference("user-orders")
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("order-payment")
    private Payment payment;
}
