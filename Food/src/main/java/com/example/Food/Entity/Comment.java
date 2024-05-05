package com.example.Food.Entity;

import com.example.Food.Entity.Food.Foods;
import com.example.Food.Entity.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentID;
    @Column(name = "parent_id")
    private int parentID;
    @Column(name = "content")
    private String content;
    @Column(name = "create_at")
    private LocalDate createAt;
    @Column(name="food_id", insertable = false, updatable = false)
    private int foodID;
    @Column(name = "user_id", insertable = false, updatable = false)
    private int userID;

    @ManyToOne()
    @JoinColumn(name = "food_id")
    @JsonBackReference("food-comments")
    private Foods food;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonBackReference("user-comments")
    private User user;

}
