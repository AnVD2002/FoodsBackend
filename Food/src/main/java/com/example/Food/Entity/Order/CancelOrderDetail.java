package com.example.Food.Entity.Order;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cancel_order_detail")
public class CancelOrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cancel_id")
    private Integer cancel_id;
    @Column(name = "reason")
    private String reason;
    @Column(name = "status")
    private boolean status;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cancelOrderDetail")
    @JsonManagedReference("orderDetail-cancel")
    private OrderDetails order_detail;

}
