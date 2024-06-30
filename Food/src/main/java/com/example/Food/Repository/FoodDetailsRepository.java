package com.example.Food.Repository;

import com.example.Food.Entity.Food.FoodDetails;
import com.example.Food.Entity.Order.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface FoodDetailsRepository extends JpaRepository<FoodDetails,Integer> {
    @Query("SELECT fd FROM FoodDetails fd WHERE fd.foodDetailID IN :foodIDs")
    List<FoodDetails> findByFoodDetailIDs(@Param("foodIDs") List<Integer> foodIds);

    @Query("select o.foodDetail from OrderDetails o where o.orderDetailID in :orderDetailIDs")
    List<FoodDetails> findByOrderDetailIDs(@Param("orderDetailIDs") List<Integer> orderDetailIDs);
}
