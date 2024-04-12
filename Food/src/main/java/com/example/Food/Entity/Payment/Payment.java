package com.example.Food.Entity.Payment;

import com.example.Food.Entity.Order.Orders;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

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
    private int paymentID;
    @Column(name = "order_id", insertable = false, updatable = false)
    private int orderID;
    @Column(name = "payment_method_id", insertable = false, updatable = false)
    private int paymentMethodID;
    @Column(name = "total")
    private double total;
    @Column(name = "status")
    private boolean status;
    @Column(name = "payment_date")
    private String paymentDate;

    @ManyToOne()
    @JsonBackReference("order-payments")
    @JoinColumn(name = "order_id")
    private Orders order;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "payment")
    @JsonManagedReference("payment_paymentDetails")
    private List<PaymentDetails> paymentDetails;

    @ManyToOne
    @JsonBackReference("paymentMethod-payments")
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;
}
