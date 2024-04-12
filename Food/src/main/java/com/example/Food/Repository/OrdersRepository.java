package com.example.Food.Repository;

import com.example.Food.Entity.Order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders , Integer> {
}
