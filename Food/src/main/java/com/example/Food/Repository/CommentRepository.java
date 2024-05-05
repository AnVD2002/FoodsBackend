package com.example.Food.Repository;

import com.example.Food.Entity.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Transactional
@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    @Query("SELECT cm FROM Comment cm WHERE cm.food.foodID = :foodID")
    List<Comment> getCommentByFoodID(@Param("foodID") int foodID);
    @Modifying
    @Query("delete Comment cm WHERE cm.parentID = :commentID")
    void deleteByCommentID(@Param("commentID") int commentID);

}
