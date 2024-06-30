package com.example.Food.Entity.Order;

import com.example.Food.Entity.Food.FoodDetails;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "order_details")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderDetailID;
    @Column(name = "food_detail_id", insertable = false, updatable = false)
    private Integer foodDetailID;
    @Column(name = "order_id" , insertable = false , updatable = false)
    private Integer orderID;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "total")
    private Double total;
    @Column(name = "cancel_id", insertable = false, updatable = false)
    private Integer cancelID;

    @ManyToOne()
    @JsonBackReference("foodDetail-orderDetails")
    @JoinColumn(name = "food_detail_id")
    private FoodDetails foodDetail;

    @ManyToOne()
    @JsonBackReference("order-orderDetails")
    @JoinColumn(name ="order_id")
    private Orders order;

    @OneToOne()
    @JsonBackReference("orderDetail-cancel")
    @JoinColumn(name = "cancel_id")
    private CancelOrderDetail cancelOrderDetail;
}
