package com.example.Food.Repository;

import com.example.Food.Entity.Food.PropertyDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyDetailsRepository extends JpaRepository<PropertyDetails,Integer> {
    @Query("SELECT DISTINCT p.property.id FROM PropertyDetails p WHERE p.propertyDetailID IN :listId")
    List<Integer> findPropertyIdsByPropertyDetailId(@Param("listId") List<Integer> listId);

    @Query("SELECT pd.property.id FROM PropertyDetails pd WHERE pd.id IN :propertyDetailIds AND pd.property.id IN (SELECT fp.property.id FROM FoodProperties fp WHERE fp.food.id = :foodId)")
    List<Integer> findPropertyDetailsByFoodIdAndPropertyDetailIds(@Param("foodId") Integer foodId, @Param("propertyDetailIds") List<Integer> propertyDetailIds);

}
