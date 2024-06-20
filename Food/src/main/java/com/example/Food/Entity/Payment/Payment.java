package com.example.Food.Entity.Payment;

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
@Table(name = "payment")
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentID;
    @Column(name = "order_id", insertable = false, updatable = false)
    private Integer orderID;
    @Column(name = "payment_method_id", insertable = false, updatable = false)
    private Integer paymentMethodID;
    @Column(name = "status")
    private boolean status;
    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @OneToOne()
    @JsonBackReference("order-payment")
    @JoinColumn(name = "order_id")
    private Orders order;


    @ManyToOne
    @JsonBackReference("paymentMethod-payments")
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;
}
