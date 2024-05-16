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
    @Query("SELECT DISTINCT p.property.propertyID FROM PropertyDetails p WHERE p.propertyDetailID IN :listId")
    List<Integer> findPropertyIdsByPropertyDetailId(@Param("listId") List<Integer> listId);

    @Query("SELECT pd.property.propertyID FROM PropertyDetails pd WHERE pd.propertyDetailID IN :propertyDetailIds AND pd.property.propertyID IN (SELECT fp.property.propertyID FROM FoodProperties fp WHERE fp.food.foodID = :foodId)")
    List<Integer> findPropertyDetailsByFoodIdAndPropertyDetailIds(@Param("foodId") Integer foodId, @Param("propertyDetailIds") List<Integer> propertyDetailIds);

    @Query("SELECT pd FROM PropertyDetails pd " +
            "JOIN pd.property p " +
            "JOIN p.foodProperties fp " +
            "WHERE fp.foodID = :foodId")
    List<PropertyDetails> getPropertyDetailsByFoodId(@Param("foodId") Integer foodId);


}
