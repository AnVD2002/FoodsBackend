package com.example.Food.Entity.User;


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
    private int UserID;
    @Column(name = "username")
    private String userName;
    @Column(name = "email")
    private String Email;
    @Column(name = "date_update")
    private LocalDate updateDate;
    @Column(name = "password")
    private String password;
    @Column(name = "number_phone")
    private String numberPhone;
    @Column(name = "decentralization_id",insertable = false,updatable = false)
    private int decentralizationID;
    @Column(name = "updatePasswordToken")
    private String updatePasswordToken;
    @Column(name = "status")
    private boolean isConfirmed;
    @Column(name = "updatePasswordTokenExpiry")
    private LocalDate updatePasswordTokenExpiry;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "decentralization_id")
    private Decentralization decentralization;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")

    @JsonManagedReference("user-orders")
    private List<Orders> orders;
}
