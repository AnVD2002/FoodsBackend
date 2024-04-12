package com.example.Food.Entity.Food;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "food_properties")
public class FoodProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_property_id")
    private int foodPropertyID;
    @Column(name = "food_id", insertable = false, updatable = false)
    private int foodID;
    @Column(name = "property_id", insertable = false, updatable = false)
    private int propertyID;

    @ManyToOne
    @JsonBackReference("food-foodProperties")
    @JoinColumn(name = "food_id")
    private Foods food;

    @ManyToOne
    @JsonBackReference("property-foodProperties")
    @JoinColumn(name = "property_id")
    private Properties property;




}
