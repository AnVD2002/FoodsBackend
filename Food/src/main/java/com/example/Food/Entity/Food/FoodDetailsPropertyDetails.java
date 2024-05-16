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
@Table(name = "food_details_property_details")
public class FoodDetailsPropertyDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "food_details_property_details_id")
    private Integer foodDetailsPropertyDetailID;
    @Column(name = "food_id",insertable = false,updatable = false)
    private Integer foodID;
    @Column(name = "property_detail_id",insertable = false,updatable = false)
    private Integer propertyDetailID;
    @Column(name = "food_detail_id",insertable = false,updatable = false)
    private Integer foodDetailID;

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
