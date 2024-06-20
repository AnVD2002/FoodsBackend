package com.example.Food.Repository;

import com.example.Food.Entity.Cart.Carts;
import com.example.Food.Entity.Cart.Promote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromoteRepository extends JpaRepository<Promote,Integer> {
    @Query("SELECT p from Promote p where p.promoteCode =:promoteCode  ")
    Optional<Promote> findPromote(@Param("promoteCode") String promoteCode);
}
