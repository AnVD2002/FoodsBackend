package com.example.Food.Entity.Food;

import com.example.Food.Entity.Comment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "foods")
public class Foods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private int foodID;
    @Column(name = "food_name")
    private String foodName;
    @Column(name = "image")
    private String image;
    @Column(name = "description")
    private String description;
    @Column(name = "food_category_id",insertable = false, updatable = false)
    private int foodCategoryID;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("food-foodDetailsPropertyDetails")
    List<FoodDetailsPropertyDetails> foodDetailsPropertyDetails;

    @ManyToOne
    @JsonBackReference("foodCategory-foods")
    @JoinColumn(name = "food_category_id")
    private FoodCategories foodCategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "food",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("food-foodProperties")
    private List<FoodProperties> foodProperties;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "food")
    @JsonManagedReference("food-comments")
    private List<Comment> comments;


}
