package com.example.Food.Repository;

import com.example.Food.Entity.Food.FoodProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodPropertiesRepository extends JpaRepository<FoodProperties,Integer> {
    @Query(value = "SELECT DISTINCT fb.foodPropertyID  FROM FoodProperties fb WHERE fb.food.foodID =:foodID")
    List<Integer> findFoodPropertyIDsByFoodID( @Param("foodID") int foodID);


}
