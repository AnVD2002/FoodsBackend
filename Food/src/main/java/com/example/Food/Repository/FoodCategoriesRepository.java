package com.example.Food.Repository;

import com.example.Food.Entity.Food.FoodCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodCategoriesRepository extends JpaRepository<FoodCategories,Integer> {
}
