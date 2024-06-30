package com.example.Food.Repository;

import com.example.Food.Entity.Order.CancelOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancelRepository extends JpaRepository<CancelOrderDetail,Integer> {
}
