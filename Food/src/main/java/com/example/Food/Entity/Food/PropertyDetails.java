package com.example.Food.Entity.Food;

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
@Table(name = "property_details")
public class PropertyDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_detail_id")
    private Integer propertyDetailID;
    @Column(name = "property_detail_name")
    private String propertyDetailName;
    @Column(name = "property_id",insertable = false, updatable = false)
    private Integer propertyID;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "propertyDetail")
    @JsonManagedReference("propertyDetail-foodDetailsPropertyDetails")
    private List<FoodDetailsPropertyDetails> foodDetailsPropertyDetails;

    @ManyToOne
    @JsonBackReference("property-propertyDetails")
    @JoinColumn(name = "property_id")
    private Properties property;



}
