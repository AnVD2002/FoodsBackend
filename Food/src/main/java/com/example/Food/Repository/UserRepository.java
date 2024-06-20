package com.example.Food.Repository;

import com.example.Food.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "select* from user where username = :name", nativeQuery = true)
    public Optional<User> FindByName(@Param("name") String name);

    @Query(value = "select u from User u where u.Email = :email")
    public Optional<User> FindByEmail(@Param("email") String email);

}
