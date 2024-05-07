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
    private int orderID;
    @Column(name = "user_id", insertable = false, updatable = false)
    private int userID;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "order_date")
    private LocalDate orderDate;
    @Column(name = "number_phone")
    private String numberPhone;
    @Column(name = "total_price")
    private String shippingAddress;
    @Column(name = "status")
    private boolean status;

    @ManyToOne()
    @JsonBackReference("user-orders")
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order")
    @JsonManagedReference("order-payment")
    private Payment payment;


}
