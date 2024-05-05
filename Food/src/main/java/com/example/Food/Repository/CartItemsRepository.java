package com.example.Food.Repository;

import com.example.Food.Entity.Cart.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Integer> {
    @Query("SELECT c from CartItems c where c.foodDetailID =:foodDetailID  ")
    Optional<CartItems> findCartItemByFoodDetailID(@Param("foodDetailID") int foodDetailID);

    @Query("SELECT c from CartItems c where c.cart.cartID =:cartID  ")
    List<CartItems> findCartItemByCartID(@Param("cartID") int cartID);


}
