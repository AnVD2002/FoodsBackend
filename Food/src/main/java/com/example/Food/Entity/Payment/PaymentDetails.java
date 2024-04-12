package com.example.Food.Entity.Payment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "payment_details")
@Builder
public class PaymentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "payment_detail_id")
    private int paymentDetailID;
    @Column(name = "payment_id", insertable = false, updatable = false)
    private int paymentID;
    @Column(name = "card_number")
    private String cardNumber;

    @ManyToOne
    @JsonBackReference("payment_paymentDetails")
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
