package com.example.Food.Repository;

import com.example.Food.Entity.Payment.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails,Integer> {
}
