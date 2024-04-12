package com.example.Food.Repository;
import com.example.Food.Entity.User.Decentralization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DecentralizationRepository extends JpaRepository<Decentralization,Integer> {
    @Query(value = "SELECT * FROM decentralization WHERE decentralization_name = :name", nativeQuery = true)
    Optional<Decentralization> findByAuthorityName(@Param("name") String name);
}
