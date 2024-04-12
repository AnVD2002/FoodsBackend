package com.example.Food.Repository;

import com.example.Food.Entity.Food.FoodDetailsPropertyDetails;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;



import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodDetailsPropertyDetailsRepository extends JpaRepository<FoodDetailsPropertyDetails,Integer> {
    @Query("SELECT fdpd.foodDetail.id FROM FoodDetailsPropertyDetails fdpd WHERE fdpd.food.id = :foodID AND fdpd.propertyDetail.id IN :propertyDetailIDs")
    List<Integer> findFoodDetailIDsByFoodIDAndPropertyDetailIDs(@Param("foodID") int foodID, @Param("propertyDetailIDs") List<Integer> propertyDetailIDs);
    @Query("SELECT DISTINCT fd.foodDetail.id FROM FoodDetailsPropertyDetails fd WHERE fd.food.id = :foodID")
    List<Integer> findDistinctFoodDetailIDsByFoodID(@Param("foodID") int foodID);
    @Modifying
    @Transactional
    @Query("DELETE FROM FoodDetailsPropertyDetails fdpd WHERE fdpd.food.id = :foodID")
    void deleteByFoodID(@Param("foodID") int foodID);
    @Query("SELECT fdpd.foodDetailsPropertyDetailID FROM FoodDetailsPropertyDetails fdpd WHERE fdpd.food.id = :foodID AND fdpd.propertyDetail.id IN :propertyDetailIDs")
    List<Integer> findIDByFoodIDAndPropertyDetailIDs(@Param("foodID") int foodID, @Param("propertyDetailIDs") List<Integer> propertyDetailIDs);

}
