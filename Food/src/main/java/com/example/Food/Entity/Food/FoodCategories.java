package com.example.Food.Entity.Food;

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
@Table(name = "food_categories")
public class FoodCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_category_id")
    private Integer foodCategoryID;
    @Column(name = "food_category_name")
    private String foodCategoryName;
    @Column(name = "image")
    private String image;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "foodCategory")
    @JsonManagedReference("foodCategory-foods")
    private List<Foods> foods;
}
