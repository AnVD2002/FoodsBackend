package com.example.Food.Entity.Payment;

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
@Table(name = "payment_method")
@Builder
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id")
    private Integer paymentMethodID;
    @Column(name = "payment_method_name")
    private String paymentMethodName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentMethod")
    @JsonManagedReference("paymentMethod-payments")
    private List<Payment> payments;
}
