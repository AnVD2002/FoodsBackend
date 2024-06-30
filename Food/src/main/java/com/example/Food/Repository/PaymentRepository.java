package com.example.Food.Repository;

import com.example.Food.Entity.Order.Orders;
import com.example.Food.Entity.Payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("SELECT SUM(o.total) FROM Orders o WHERE MONTH(o.orderDate) = MONTH(CURRENT_DATE) AND YEAR(o.orderDate) = YEAR(CURRENT_DATE)")
    public Double findTotalForCurrentMonth();

    @Query("SELECT o FROM Orders o WHERE MONTH(o.orderDate) = MONTH(CURRENT_DATE) AND YEAR(o.orderDate) = YEAR(CURRENT_DATE)")
    public List<Orders> findOrderForCurrentMonth();
}
