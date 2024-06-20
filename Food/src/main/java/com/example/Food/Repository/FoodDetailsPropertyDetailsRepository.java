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
    @Query("SELECT fdpd.foodDetail.foodDetailID FROM FoodDetailsPropertyDetails fdpd " +
            "WHERE fdpd.food.foodID = :foodID AND fdpd.propertyDetail.propertyDetailID IN (:propertyDetailIDs) " +
            "GROUP BY fdpd.foodDetail.foodDetailID " +
            "HAVING COUNT(DISTINCT fdpd.propertyDetail.propertyDetailID) = " +
            "(SELECT COUNT(DISTINCT pd.propertyDetailID) FROM PropertyDetails pd WHERE pd.propertyDetailID IN (:propertyDetailIDs))")
    List<Integer> findFoodDetailIDsByFoodIDAndPropertyDetailIDs(@Param("foodID") Integer foodID, @Param("propertyDetailIDs") List<Integer> propertyDetailIDs);

    @Query("SELECT DISTINCT fd.foodDetail.foodDetailID FROM FoodDetailsPropertyDetails fd WHERE fd.food.foodID = :foodID")
    List<Integer> findDistinctFoodDetailIDsByFoodID(@Param("foodID") Integer foodID);

    @Modifying
    @Transactional
    @Query("DELETE FROM FoodDetailsPropertyDetails fdpd WHERE fdpd.food.foodID = :foodID")
    void deleteByFoodID(@Param("foodID") Integer foodID);

    @Query("SELECT fdpd.foodDetailsPropertyDetailID FROM FoodDetailsPropertyDetails fdpd WHERE fdpd.food.foodID = :foodID AND fdpd.propertyDetail.propertyDetailID IN :propertyDetailIDs")
    List<Integer> findIDByFoodIDAndPropertyDetailIDs(@Param("foodID") Integer foodID, @Param("propertyDetailIDs") List<Integer> propertyDetailIDs);

    @Modifying
    @Transactional
    @Query("DELETE FROM FoodDetailsPropertyDetails fdpd WHERE fdpd.foodDetail.foodDetailID = :foodDetailID")
    void findFoodDetailID(@Param("foodDetailID") Integer foodDetailID);

}
