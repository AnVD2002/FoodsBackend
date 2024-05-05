package com.example.Food.Repository;

import com.example.Food.Entity.Cart.CartItems;
import com.example.Food.Entity.Cart.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Carts, Integer> {
    @Query("SELECT c from Carts c where c.user.UserID =:userID  ")
    Optional<Carts> findCartByUserID(@Param("userID") int userID);


}
