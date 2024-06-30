package com.example.Food.Repository;

import com.example.Food.Entity.Order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    @Query("SELECT o FROM Orders o WHERE o.payment.status = false AND o.status = true")
    List<Orders> findNewOrders();
}
