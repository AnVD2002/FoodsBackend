package com.example.Food.Repository;

import com.example.Food.Entity.Food.Foods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodsRepository extends JpaRepository<Foods,Integer> , JpaSpecificationExecutor<Foods> {

}
