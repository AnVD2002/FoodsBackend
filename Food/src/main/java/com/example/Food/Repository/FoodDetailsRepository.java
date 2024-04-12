package com.example.Food.Repository;

import com.example.Food.Entity.Food.FoodDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodDetailsRepository extends JpaRepository<FoodDetails,Integer> {
}
