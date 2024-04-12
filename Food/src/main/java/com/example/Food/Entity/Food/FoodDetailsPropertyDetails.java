package com.example.Food.Entity.Food;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


import com.example.Food.Entity.Order.OrderDetails;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "food_details_property_details")
public class FoodDetailsPropertyDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "food_details_property_details_id")
    private int foodDetailsPropertyDetailID;
    @Column(name = "food_id",insertable = false,updatable = false)
    private int foodID;
    @Column(name = "property_detail_id",insertable = false,updatable = false)
    private int propertyDetailID;
    @Column(name = "food_detail_id",insertable = false,updatable = false)
    private int foodDetailID;

    @ManyToOne()
    @JsonBackReference("propertyDetail-foodDetailsPropertyDetails")
    @JoinColumn(name = "property_detail_id")
    private PropertyDetails propertyDetail;

    @ManyToOne()
    @JsonBackReference("food-foodDetailsPropertyDetails")
    @JoinColumn(name = "food_id")
    private Foods food;
    
    @ManyToOne()
    @JsonBackReference("foodDetail-foodDetailsPropertyDetails")
    @JoinColumn(name = "food_detail_id")
    private FoodDetails foodDetail;


}
