package com.example.Food.Repository;

import com.example.Food.Entity.Order.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
    @Query("SELECT od FROM OrderDetails od WHERE od.order.orderID IN :orderIDs")
    List<OrderDetails> findByOrderIDs(@Param("orderIDs") List<Integer> orderIDs);

    @Query("SELECT od.orderDetailID FROM OrderDetails od ")
    List<Integer> findOrderDetailIDs();
    @Query("SELECT od FROM OrderDetails od WHERE od.cancelOrderDetail IS NOT NULL")
    List<OrderDetails> findOrderDetailsWithNonEmptyCancelOrderDetail();
}
