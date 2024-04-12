package com.example.Food.Repository;

import com.example.Food.Entity.Payment.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
}
