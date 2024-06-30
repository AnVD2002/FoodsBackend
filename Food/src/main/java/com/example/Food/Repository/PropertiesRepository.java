package com.example.Food.Repository;

import com.example.Food.Entity.Food.Properties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertiesRepository extends JpaRepository<Properties,Integer> {

}
